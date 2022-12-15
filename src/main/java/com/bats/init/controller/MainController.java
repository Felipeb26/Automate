package com.bats.init.controller;

import com.bats.init.config.Configs;
import com.bats.init.config.Exceptions;
import com.bats.init.config.Format;
import com.bats.init.service.Background;
import com.bats.init.service.Console;
import com.bats.init.service.ExecuteOnTerminal;
import com.bats.init.service.OpenTerminal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.ClassLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.Objects.nonNull;

@Component
public class MainController implements Initializable {

    private final ExecuteOnTerminal execute = new ExecuteOnTerminal();
    private final OpenTerminal terminal = new OpenTerminal();
    private final Format format = new Format();
    private final Configs config = new Configs();

    private List<String> paths = new ArrayList<>();

    @FXML
    private static DirectoryChooser directoryChooser;
    @FXML
    private Button btnOpenDir, btnAddCommand, btnStart, btnStop, btnMinimize, btnClose, btnFull;
    @FXML
    private ListView<String> listPath, listCommand, directoryName;
    @FXML
    private TextField inputCommand;
    @FXML
    private Label lblErro, lbllistQuant;
    @FXML
    private TextArea console;
    private PrintStream ps;
    private Stage stage;
    private static double yoffset, xoffset;
    private Thread th;
    @FXML
    private ImageView image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listCommand.getItems().add("--commands to execute--");
        ps = new PrintStream(new Console(console));
        btnStart.setDisable(true);
        getPath();
        stop();
        start();
        addCommand();
        image.setVisible(false);
    }

    private void getPath() {
        directoryChooser = new DirectoryChooser();
        btnOpenDir.setOnAction(Event -> {
            format.checkList(listPath, directoryName);
            config.configureFileChooser(directoryChooser);
            paths = config.showSubFolders(directoryChooser);
            setList();
        });
    }

    private void setList() {
        for (var p : paths) {
            listPath.getItems().add(p);
            directoryName.getItems().add(format.pathName(p));
        }
        if (!paths.isEmpty()) {
            var count = paths.size();
            lbllistQuant.setText(String.format("%s diretorios localizados", count));
        }
        format.enableBtnByTwoLists(listCommand, listPath, btnStart);
    }

    @FXML
    private void removeItem(MouseEvent mouseEvent) {
        var item = listPath.getSelectionModel().getSelectedIndex();
        var iten = directoryName.getSelectionModel().getSelectedIndex();
        if (item > -1) {
            listPath.getItems().remove(item);
            directoryName.getItems().remove(item);
            format.updateSize(listPath, lbllistQuant);
        } else if (iten > -1) {
            directoryName.getItems().remove(iten);
            listPath.getItems().remove(iten);
            format.updateSize(listPath, lbllistQuant);
        }
    }

    private void addCommand() {
        btnAddCommand.setOnAction(Event -> {
            String value = inputCommand.getText();
            if (!value.isEmpty() && !value.isBlank()) {
                config.List(listCommand);
                inputCommand.setText("");
                listCommand.getItems().add(value);
                format.enableBtnByTwoLists(listCommand, listPath, btnStart);
            } else {
                lblErro.setText("necessario informar comando!");
            }
        });
    }

    private void start() {
        btnStart.setOnAction(Event -> {
            try {
                ps = new PrintStream(new Console(console));
                format.resetConsole(console);
                format.resetLabel(lblErro);
                image.setVisible(true);
                Background background = new Background(listPath, listCommand, ps);
                background.messageProperty().addListener((observable, oldValue, newValue) -> {
                    var value = String.valueOf(newValue);
                    if (value.equals("finite")) {
                        lblErro.setText("Finalizado comandos informadas!");
                        format.resetCommandList(listCommand);
                    } else {
                        console.appendText(value);
                    }
                });

                th = new Thread(background);
                th.setDaemon(true);
                th.start();


//                CounterTask task = new CounterTask(150000L);
//                task.valueProperty().addListener((observable, oldValue, newValue) -> lbllistQuant.setText(String.valueOf(newValue)));
//
//                progress.progressProperty().bind(task.progressProperty());
//                Thread thread = new Thread(task);
//                thread.setDaemon(true);
//                thread.start();
//                System.out.println(background.get());
            } catch (Exception e) {
                Exceptions.ToText(e, ps);
                System.out.println(e.getMessage());
            }
        });
    }

    private void stop() {
        btnStop.setOnAction(event -> {
            if (nonNull(th) && th.isAlive()) {
                th.interrupt();
                lblErro.setText("Comando interrompido!");
            } else {
                lblErro.setText("Projeto não está rodando!");
            }
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

    private void image(){

    }

}