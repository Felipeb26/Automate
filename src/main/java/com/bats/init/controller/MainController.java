package com.bats.init.controller;

import com.bats.init.config.Configs;
import com.bats.init.config.Format;
import com.bats.init.service.Console;
import com.bats.init.service.ExecuteOnTerminal;
import com.bats.init.service.OpenTerminal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class MainController implements Initializable {

    private static final String path = System.getProperty("user.home");
    private ExecuteOnTerminal execute = new ExecuteOnTerminal();
    private OpenTerminal terminal = new OpenTerminal();
    private Format format = new Format();
    private Configs config = new Configs();
    private List<String> paths = new ArrayList<>();

    @FXML
    private static DirectoryChooser directoryChooser;
    @FXML
    private Stage stage;
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
    private static String s;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listCommand.getItems().add("--commands to execute--");
        ps = new PrintStream(new Console(console));
        btnStart.setDisable(true);
        System.out.println(ps);
        System.err.println(ps);
        getPath();
        start();
        addCommand();
    }

    public void configureFileChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setInitialDirectory(new File(path));
        directoryChooser.setTitle("Open directory with all pastes");
    }

    private void getPath() {
        stage = new Stage();
        directoryChooser = new DirectoryChooser();
        btnOpenDir.setOnAction(Event -> {
            configureFileChooser(directoryChooser);
            showSubFolders(directoryChooser);
        });
    }

    public void showSubFolders(DirectoryChooser directoryChooser) {
        try {
            final File path = directoryChooser.showDialog(stage);
            if (nonNull(path)) {
                var folder = path.getAbsolutePath();
                List<Path> subfolder = Files.walk(Path.of(folder), 1)
                        .filter(Files::isDirectory)
                        .collect(Collectors.toList());
                subfolder.parallelStream().distinct().forEach(it -> paths.add(it.toString()));
                setList();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setList() {
        for (var p : paths) {
            listPath.getItems().add(p);
        }
    }

    @FXML
    private void removeItem(MouseEvent mouseEvent) {
        var item = listPath.getSelectionModel().getSelectedIndex();
        listPath.getItems().remove(item);
    }

    private void addCommand() {
        btnAddCommand.setOnAction(Event -> {
            String value = inputCommand.getText();
            if (nonNull(value) && !value.isBlank()) {
                config.List(listCommand);
                listCommand.getItems().add(value);
                btnStart.setDisable(false);
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
                var list = format.toList(listPath);
                for (String value : list) {
                    var command = format.toListOfCommands(listCommand);
                    for (var exec : command) {
                        System.out.printf("exec: %s \t path: %s", exec, value);
                        var result = execute.execs(exec, value);
                        for (var text : result) {
                            console.appendText(text + "\n");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
