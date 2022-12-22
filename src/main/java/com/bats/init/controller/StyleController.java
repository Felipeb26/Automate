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
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

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

    private static String backColor, textColor, borderColor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salvarArquivo();
        toDeafultStyle();
        btnSalvar.setText("SALVAR ESTILO");
        btnDeafult.setText("ESTILO");
    }

    @FXML
    public void changeColor(ActionEvent event) {
        var color = colorBack.getValue();
        backColor = format.rgbFromColorPicker(color);
        lblBack.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    @FXML
    public void changeColorText(ActionEvent event) {
        var color = colorText.getValue();
        textColor = format.rgbFromColorPicker(color);
        lblText.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    @FXML
    public void changeColorBorder(ActionEvent event) {
        var color = colorBorder.getValue();
        borderColor = format.rgbFromColorPicker(color);
        lblBorder.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    private void salvarArquivo() {
        btnSalvar.setOnMouseClicked(event -> {
            try {
                backColor = caseBackIsNull(backColor);
                textColor = caseTextIsNull(textColor);
                borderColor = caseBorderIsNull(borderColor);
                utils.writeFile(backColor, textColor, borderColor);
                lblResponse.setText("Arquivo de estilo criado!");
//                configs.changeScene(StyleController.class, "main", null, stage, stage.isFullScreen());
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

    public String caseBackIsNull(String s) {
        if (s.isBlank() || s.isEmpty()) {
            return "12,12,12,0.3";
        } else {
            return s;
        }
    }

    public String caseBorderIsNull(String s) {
        if (s.isBlank() || s.isEmpty()) {
            return "255,255,255";
        } else {
            return s;
        }
    }

    public String caseTextIsNull(String s) {
        if (s.isBlank() || s.isEmpty()) {
            return "12,12,12";
        } else {
            return s;
        }
    }
}
