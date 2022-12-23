package com.bats.init.controller;

import com.bats.init.JavaFxApplication;
import com.bats.init.config.Configs;
import com.bats.init.config.Exceptions;
import com.bats.init.config.FilesUtils;
import com.bats.init.config.Format;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static java.util.Objects.nonNull;

@Component
public class StyleController implements Initializable {

    private static final Format format = new Format();
    private static final Configs configs = new Configs();
    private static final FilesUtils utils = new FilesUtils();
    @FXML
    private ColorPicker colorBack, colorBorder, colorText;
    @FXML
    private Label lblBack, lblText, lblBorder;
    @FXML
    private Button btnSalvar, btnDeafult;
    @FXML
    private Label lblResponse;
    @FXML
    private Stage stage;
    @FXML
    private HBox hbox1, hbox3;
    private static String backColor, textColor, borderColor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salvarArquivo();
        toDeafultStyle();
    }

    @FXML
    public void changeColor(ActionEvent event) {
        var color = colorBack.getValue();
        backColor = format.rgbFromColorPicker(color);
        hbox1.setBackground(new Background(new BackgroundFill(color, null, null)));
        lblBack.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    @FXML
    public void changeColorText(ActionEvent event) {
        var color = colorText.getValue();
        textColor = format.rgbFromColorPicker(color);
        lblText.setTextFill(color);
    }

    @FXML
    public void changeColorBorder(ActionEvent event) {
        var color = colorBorder.getValue();
        borderColor = format.rgbFromColorPicker(color);
        hbox3.setStyle(String.format("-fx-border-color: rgba(%s);", borderColor));
        lblBorder.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    private void salvarArquivo() {
        btnSalvar.setOnMouseClicked(event -> {
            try {

                backColor = nonNull(backColor) ? backColor : "0, 0, 0, 0.3";
                textColor = nonNull(textColor) ? textColor : "240, 240, 240";
                borderColor = nonNull(borderColor) ? borderColor : "255, 255, 255";
                utils.writeFile(backColor, textColor, borderColor);
                lblResponse.setText("Arquivo de estilo criado!");
            } catch (Exception e) {
                Exceptions.ToText(e);
            }
        });
    }

    private void toDeafultStyle() {
        btnDeafult.setOnMouseClicked(event -> {
            try {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                var deleted = utils.deleteFile();
                if (deleted) {
                    lblResponse.setText("Arquivo de estilo deletado indo para o default!");
                    Thread.sleep(500);
                    configs.changeScene(StyleController.class, "main", null, stage, stage.isFullScreen());
                } else {
                    lblResponse.setText("O estilo default não pode ser deletado!");
                }
            } catch (Exception e) {
                Exceptions.ToText(e);
            }
        });
    }

}
