package com.bats.init;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private static Parent root;
    private static Scene scene;

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
        var xml = JavaFxApplication.class.getResource("/fxml/main.fxml");
        if(xml != null){
            root = FXMLLoader.load(xml);
            scene = new Scene(root);
        }

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add("css/index.css");

        var path = JavaFxApplication.class.getResourceAsStream("/image/icon.png");
        if (path != null) {
            stage.getIcons().add(new Image(path));
        }
        stage.setScene(scene);
        stage.setMinHeight(700);
        stage.setMinWidth(500);
        stage.show();
    }

}
