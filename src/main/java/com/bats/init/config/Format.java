package com.bats.init.config;

import javafx.scene.control.ListView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class Format {

    public List<String> toList(ListView<String> listPath){
        return listPath.getItems().stream().distinct()
                .map(Objects::toString).collect(Collectors.toList());
    }

    public List<String> toListOfCommands(ListView<String> listCommand) {
        return listCommand.getItems().stream().distinct().skip(1)
                .map(Objects::toString).collect(Collectors.toList());
    }
}
