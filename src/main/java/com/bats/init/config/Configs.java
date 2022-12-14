package com.bats.init.config;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.File;
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
                subfolder.parallelStream().distinct().map(this::getPath).filter(this::just).forEach(paths::add);
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
}
