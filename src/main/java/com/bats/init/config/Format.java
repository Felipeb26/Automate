package com.bats.init.config;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class Format {

    public List<String> toList(ListView<String> listPath) {
        return listPath.getItems().stream().distinct()
                .map(Objects::toString).collect(Collectors.toList());
    }

    public List<String> toListOfCommands(ListView<String> listCommand) {
        return listCommand.getItems().stream().distinct().skip(1)
                .map(Objects::toString).collect(Collectors.toList());
    }

    public void resetCommandList(ListView<String> listView) {
        var itens = listView.getItems().stream().skip(1).collect(Collectors.toList());
        listView.getItems().removeAll(itens);

    }

    public void resetList(ListView<String> listView) {
        var itens = new ArrayList<>(listView.getItems());
        listView.getItems().removeAll(itens);
    }

    public void resetConsole(TextArea textArea) {
        if (!textArea.getText().isEmpty() || !textArea.getText().isBlank()) {
            textArea.clear();
        }
    }

    public void enableBtnByTwoLists(ListView<String> list1, ListView<String> list2, Button button) {
        var lista1 = list1.getItems().stream().collect(Collectors.toList());
        var lista2 = list2.getItems().stream().collect(Collectors.toList());
        if (!lista1.isEmpty() && !lista2.isEmpty()) {
            button.setDisable(false);
        }
    }

}
