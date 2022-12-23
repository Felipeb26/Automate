package com.bats.init.controller;

import com.bats.init.config.Exceptions;
import com.bats.init.service.QrCodeService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ShareController implements Initializable {

    private static final QrCodeService service = new QrCodeService();
    private static final String path = System.getProperty("user.home");
    @FXML
    private ImageView image, emailImage,githubImage,linkedinImage;
    @FXML
    private Pane pane;
    @FXML
    private Hyperlink linkedin, github, email;
    private final String[] urls = {"https://www.linkedin.com/in/felipebatista-silva/",
            "https://github.com/Felipeb26", "mailto:felipeb2silva@gmail.com"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setImageICons();
        createImage();
        setLinks();
        setImage();
        imageFit();
    }
    private void createImage(){
        if(!new File(path+"/qr.png").exists()){
            service.generateQr();
        }
    }
    public void setImage() {
        try {
            File file = new File(path + "/qr.png");
            Image image1 = new Image(file.getPath());
            image.setImage(image1);
        } catch (Exception e) {
            Exceptions.ToText(e);
        }
    }
    private void imageFit() {
        pane.widthProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                image.setFitWidth(pane.getWidth() / 1.1);
            }
        });
    }
    private void setLinks() {
        linkedin.setText("black");
        github.setText("black");
        email.setText("black");

        linkedin.setText("Para verificar meu linkedin".toUpperCase());
        github.setText("Para verificar meu github   ".toUpperCase());
        email.setText("Para verificar meu email     ".toUpperCase());
        try {
            Desktop desktop = Desktop.getDesktop();
            linkedin.setOnAction(event -> {
                try {
                    var link = stringToLink(urls[0]);
                    desktop.browse(link);
                } catch (Exception e) {
                    Exceptions.ToText(e);
                }
            });
            github.setOnAction(event -> {
                try {
                    var link = stringToLink(urls[1]);
                    desktop.browse(link);
                } catch (Exception e) {
                    Exceptions.ToText(e);
                }
            });
            email.setOnAction(event -> {
                try {
                    var link = stringToLink(urls[2]);
                    desktop.browse(link);
                } catch (Exception e) {
                    Exceptions.ToText(e);
                }
            });
        } catch (Exception e) {
            Exceptions.ToText(e);
        }
    }
    public URI stringToLink(String link) throws Exception {
        return new URL(link).toURI();
    }
    public void setImageICons(){
       var email = ShareController.class.getResource("/image/email.png").toExternalForm();
       var github = ShareController.class.getResource("/image/github.png").toExternalForm();
       var linkedin = ShareController.class.getResource("/image/linkedin.png").toExternalForm();

        emailImage.setImage(new Image(email));
        githubImage.setImage(new Image(github));
        linkedinImage.setImage(new Image(linkedin));
    }
}
