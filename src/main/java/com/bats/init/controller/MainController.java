package com.bats.init.controller;

import com.bats.init.service.OpenTerminal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;
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
    private OpenTerminal terminal = new OpenTerminal();
    private List<String> paths = new ArrayList<>();

    @FXML
    private static DirectoryChooser directoryChooser;
    @FXML
    private Stage stage;
    @FXML
    private Button btnOpenDir;
    @FXML
    private ListView<String> listPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getPath();
        listViewProps();
    }

    public void configureFileChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setInitialDirectory(new File(path));
        directoryChooser.setTitle("Open Resource File");
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

    private void listViewProps() {
        List<String> caminhos = new ArrayList<>();
        listPath.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            removeItem();
//            listPath.getItems().removeAll(caminhos);
//            paths.removeAll(caminhos);
//            paths.forEach(System.out::println);
        });
    }

    private void removeItem(){
        var item = listPath.getSelectionModel().getSelectedIndex();
        listPath.getItems().remove(item);
    }
}
