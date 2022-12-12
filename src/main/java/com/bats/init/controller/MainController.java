package com.bats.init.controller;

import com.bats.init.config.Configs;
import com.bats.init.config.Exceptions;
import com.bats.init.config.Format;
import com.bats.init.service.Background;
import com.bats.init.service.Console;
import com.bats.init.service.ExecuteOnTerminal;
import com.bats.init.service.OpenTerminal;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
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
    private static String s;

    @FXML
    private static DirectoryChooser directoryChooser;
    @FXML
    private Button btnOpenDir, btnAddCommand, btnStart;
    @FXML
    private ListView<String> listPath, listCommand;
    @FXML
    private TextField inputCommand;
    @FXML
    private Label lblErro;
    @FXML
    private TextArea console;
    private PrintStream ps;
    private Thread th;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listCommand.getItems().add("--commands to execute--");
        ps = new PrintStream(new Console(console));
        btnStart.setDisable(true);
//        System.out.println(ps);
//        System.err.println(ps);
        getPath();
        start();
        addCommand();
    }

    private void getPath() {
        directoryChooser = new DirectoryChooser();
        btnOpenDir.setOnAction(Event -> {
            config.configureFileChooser(directoryChooser);
            paths = config.showSubFolders(directoryChooser);
            setList();
        });
    }

    private void setList() {
        for (var p : paths) {
            listPath.getItems().add(p);
        }
    }

    @FXML
    private void removeItem(MouseEvent mouseEvent) {
        var item = listPath.getSelectionModel().getSelectedIndex();
        if (item > -1) {
            listPath.getItems().remove(item);
        }
    }

    private void addCommand() {
        btnAddCommand.setOnAction(Event -> {
            String value = inputCommand.getText();
            if (nonNull(value) && !value.isBlank()) {
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
                System.out.println(ps);
                System.err.println(ps);
                format.resetConsole(console);
                var list = format.toList(listPath);
                for (String value : list) {
                    var command = format.toListOfCommands(listCommand);
                    for (var exec : command) {
                        var result = execute.execs(exec, value, ps);
                        if (result.isEmpty()) {
                            System.out.println(result);
                        } else {
                            for (var text : result) {
                                text = text + "\n";
                                console.appendText(text);
                            }
                        }
                    }
                }
                format.resetCommandList(listCommand);
//                Background background = new Background(listPath, listCommand, console, ps);
//                Platform.runLater(() ->{
//                    th = new Thread(background);
//                    th.start();
//                });
            } catch (Exception e) {
                Exceptions.ToText(e, ps);
                System.out.println(e.getMessage());
            }
        });
    }
}
