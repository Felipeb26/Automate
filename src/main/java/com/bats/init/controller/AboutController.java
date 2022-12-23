package com.bats.init.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class AboutController implements Initializable {

    @FXML
    private Label lblAbout;
    @FXML
    private VBox Image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground();
        labelWrap();
    }

    private void setBackground() {
        String url = AboutController.class.getResource("/image/all.png").toExternalForm();
        Image.setStyle(String.format("-fx-background-image: url(%s)", url));
    }

    public void labelWrap() {
        lblAbout.setWrapText(true);
        lblAbout.setTextAlignment(TextAlignment.JUSTIFY);
        lblAbout.setTranslateX(25);
        lblAbout.setTranslateY(25);
        lblAbout.setFont(Font.font(12));

        lblAbout.setPrefWidth(250);
        lblAbout.setPrefHeight(500);
        lblAbout.setPadding(new Insets(0, 20, 0, 0));
        lblAbout.setText("\tPara iniciar o projeto é necessario, escolher em quais pastas será executado os comandos, " +
                "após sua descisão digite no input 'digite o comando' e aperte ENTER ou no botão 'add command', " +
                "depois clique em start e em alguns segundos verá executando os comandos nas pastas selecionadas " +
                "na area de texto abaixo, caso queira interromper a execução dos comandos clique em stop!\n\n" +
                "\tPara impedir que uma pasta receba o comando basta clicar na lista onde contém seu nome ou onde" +
                " contém o caminho da pasta.\n\n" +
                "\tPara alterar o comando na lista basta clicar duas vezes no comando dentro da 'lista de comandos' e " +
                "depois da sua alteração apenas clique em qualquer outro lugar na tela.\n\n" +
                "\tCaso não queira usar o estilo do sistema clique em about e depois styles selecione o botão de cor de fundo" +
                ", a cor do texto e a cor da borda.\n\n" +
                "\tPara entrar em contato com por meio do linkedin, github ou email basta clicar nos links.\tcaso queira baixar" +
                " ou ajudar alguem a baixar o automate basta escanear o qrcode!\n\n" +
                "\tObs: features estão sendo lançadas constantemente recomendado sempre ficar alerta sobre atuallizações\n" +
                "obrigado BatsWorks!\n\n");
    }

}
