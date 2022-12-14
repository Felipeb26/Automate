package com.bats.init.controller;

import com.bats.init.config.Configs;
import com.bats.init.config.Exceptions;
import com.bats.init.config.Format;
import com.bats.init.service.Console;
import com.bats.init.service.ExecuteOnTerminal;
import com.bats.init.service.OpenTerminal;
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

    @FXML
    private static DirectoryChooser directoryChooser;
    @FXML
    private Button btnOpenDir, btnAddCommand, btnStart;
    @FXML
    private ListView<String> listPath, listCommand, directoryName;
    @FXML
    private TextField inputCommand;
    @FXML
    private Label lblErro, lbllistQuant;
    @FXML
    private TextArea console;
    private PrintStream ps;
    private Thread th;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listCommand.getItems().add("--commands to execute--");
        ps = new PrintStream(new Console(console));
        btnStart.setDisable(true);
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
            directoryName.getItems().add(format.pathName(p));
        }
        if (!paths.isEmpty()) {
            var count = paths.size();
            lbllistQuant.setText(String.format("%s diretorios localizados", count));
        }
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
                        var num = value.lastIndexOf("/");
                        if (num == -1) {
                            num = value.lastIndexOf("\\");
                        }
                        String pathName = value.substring(num);
                        String message = String.format("Executando comando: %s\t na Pasta: %s\n", exec, pathName);
                        console.appendText(message);
                        if (result.isEmpty()) {
                            System.out.println(result);
                        } else {
                            for (var text : result) {
                                text = text + "\n";
                                console.appendText(text);
                            }
                        }
                        message = message.replace("Executando", "Finalizando") + "\n";
                        console.appendText(message);
                    }
                }
                format.resetCommandList(listCommand);
                lblErro.setText("Finalizado todos comandos!");
//
//                Background background = new Background(listPath, listCommand, console, ps, lblErro);
//                new Thread(background).start();
            } catch (Exception e) {
                Exceptions.ToText(e, ps);
                System.out.println(e.getMessage());
            }
        });
    }
}
