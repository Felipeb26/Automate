package com.bats.init.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AboutController implements Initializable {

    @FXML
    private Label lblAbout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelWrap();
    }

    public void labelWrap() {
        lblAbout.setWrapText(true);
        lblAbout.setTextAlignment(TextAlignment.JUSTIFY);
        lblAbout.setTranslateX(25);
        lblAbout.setTranslateY(25);
        lblAbout.setFont(Font.font(14));

        lblAbout.setPrefWidth(250);
        lblAbout.setPadding(new Insets(0, 20, 0, 0));
        lblAbout.setText("\tPara iniciar o projeto é necessario, escolher em quais pastas será executado os comandos, " +
                "após sua descisão digite no input 'digite o comando' e aperte ENTER ou no botão 'add command', " +
                "depois clique em start e em alguns segundos verá executando os comandos nas pastas selecionadas " +
                "na area de texto abaixo, caso queira interromper a execução dos comandos clique em stop!\n\n" +
                "\tPara impedir que uma pasta receba o comando basta clicar na lista onde contém seu nome ou onde" +
                " contém o caminho da pasta.\n\n" +
                "\tPara alterar o comando na lista basta clicar duas vezes no comando dentro da 'lista de comandos' e " +
                "depois da sua alteração apenas clique em qualquer outro lugar na tela." +
                "\tObs: features estão sendo lançadas constantemente recomendado sempre ficar alerta sobre atuallizações\n" +
                "obrigado BatsWorks!");
    }

}
