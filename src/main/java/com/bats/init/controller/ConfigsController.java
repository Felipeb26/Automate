package com.bats.init.controller;


import com.bats.init.config.Configs;
import com.bats.init.config.Exceptions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class ConfigsController implements Initializable {

    @FXML
    private StackPane stackPane;
    private static final Configs config = new Configs();
    @FXML
    private double xoffset, yoffset;
    @FXML
    private Stage stage;

    @FXML
    private Button btnMain, abouts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toMain();
        set();
    }

    private void toMain() {
        btnMain.setOnAction(event -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            config.changeScene(MainController.class, "main", null, stage);
        });
    }

    @FXML
    public void onPanePressed(MouseEvent me) {
        stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        xoffset = stage.getX() - me.getScreenX();
        yoffset = stage.getY() - me.getScreenY();
    }

    @FXML
    public void onPaneDrag(MouseEvent me) {
        stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.setX(xoffset + me.getScreenX());
        stage.setY(yoffset + me.getScreenY());
    }

    @FXML
    public void closeStage(MouseEvent me) {
        stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void miniStage(MouseEvent me) {
        stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.setIconified(!stage.isIconified());
    }

    @FXML
    public void fullStage(MouseEvent me) {
        stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    public void set() {
        try {
            var url = getClass().getResource("/fxml/about.fxml");
            if (url != null) {
                Parent root = FXMLLoader.load(url);
                abouts.setOnAction(event -> {
                    stackPane.getChildren().removeAll();
                    stackPane.getChildren().setAll(root);
                });
            }
            System.out.println("url é" + url);
        } catch (Exception e) {
            e.printStackTrace();
            Exceptions.ToText(e, null);
        }
    }


}
