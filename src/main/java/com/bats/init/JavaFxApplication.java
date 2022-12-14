package com.bats.init;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import static java.util.Objects.requireNonNull;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                ac -> {
                    ac.registerBean(Application.class, () -> JavaFxApplication.this);
                    ac.registerBean(Parameters.class, this::getParameters);
                    ac.registerBean(HostServices.class, this::getHostServices);
                };
        this.springContext = new SpringApplicationBuilder()
                .sources(BatsSpringApplication.class)
                .headless(false)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void stop() {
        this.springContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(requireNonNull(JavaFxApplication.class.getResource("/fxml/main.fxml")));
        stage.setScene(new Scene(parent));
        stage.setMinHeight(700);
        stage.setMinWidth(500);
//        stage.setResizable(false);
        stage.setTitle("AUTOMATE");
        stage.show();
    }
}
