package com.bats.init.config;

import com.bats.init.service.ExecuteOnTerminal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class Configs {

    private static final String path = System.getProperty("user.home");

    private final ExecuteOnTerminal execute = new ExecuteOnTerminal();
    @FXML
    private Stage stage;

    public void List(ListView<String> listView) {
        var itens = listView.getItems().stream().count();
        if (itens > 5) {
            listView.getItems().remove(5);
        }
    }

    public List<String> showSubFolders(DirectoryChooser directoryChooser) {
        try {
            List<String> paths = new ArrayList<>();
            stage = new Stage();
            final File path = directoryChooser.showDialog(stage);
            if (nonNull(path)) {
                var folder = path.getAbsolutePath();
                List<Path> subfolder = Files.walk(Path.of(folder), 1).filter(Files::isDirectory).collect(Collectors.toList());
                subfolder.stream().distinct().map(this::getPath).filter(this::just).forEach(paths::add);
            }
            return paths.stream().sorted(Comparator.comparing(String::length)).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private boolean just(String s) {
        if (s.isBlank() || s.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private String getPath(Path path) {
        var paths = path.toAbsolutePath();
        var place = paths.toString();
        var num = place.lastIndexOf("\\");
        if (num == -1) {
            num = place.lastIndexOf("/");
        }
        place = place.substring(num + 1);
        if (!place.startsWith(".") && !place.startsWith("node")) {
            return path.toString();
        }
        return "";
    }

    public void configureFileChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setInitialDirectory(new File(path));
        directoryChooser.setTitle("Open directory with all pastes");
    }

    public void changeScene(Class<?> classe, String path, PrintStream ps, Stage stag) {
        try {
            if (!path.startsWith("/fxml/")) {
                path = "/fxml/" + path + ".fxml";
            }
            var xml = classe.getResource(path);
            if (xml != null) {
                Parent root = FXMLLoader.load(xml);
                Scene scene = new Scene(root);
                stag.close();

                stage = new Stage();
                scene.setFill(Color.TRANSPARENT);
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.getStylesheets().add("css/index.css");

                var icon = classe.getResourceAsStream("/image/icon.png");
                if (icon != null) {
                    stage.getIcons().add(new Image(icon));
                }
                stage.setScene(scene);
                stage.setMinHeight(700);
                stage.setMinWidth(500);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Exceptions.ToText(e, ps);
        }
    }

    public void npmCahche() {
        execute.execs("npm clean cache --force", path, null);
    }
}
