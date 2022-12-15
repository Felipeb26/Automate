package com.bats.init.config;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    public void updateSize(ListView<String> list, Label label) {
        var lista = list.getItems().stream().collect(Collectors.toList());
        if (!lista.isEmpty()) {
            var count = lista.size();
            label.setText(String.format("%s diretorios listados!", count));
        }
    }

    public String pathName(String s) {
        var i = s.lastIndexOf("/");
        if (i == -1) {
            i = s.lastIndexOf("\\");
        }
        return s.substring(i + 1);
    }

    public void checkList(ListView<String> listPath, ListView<String> directoryName) {
        var lista1 = listPath.getItems().stream().collect(Collectors.toList());
        var list2 = directoryName.getItems().stream().collect(Collectors.toList());
        if(!lista1.isEmpty() || !list2.isEmpty()){
            listPath.getItems().clear();
            directoryName.getItems().clear();
        }
    }

    public void resetLabel(Label lblErro) {
       var lbl = lblErro.getText();
       if(!lbl.isEmpty() || !lbl.isBlank()){
           lblErro.setText("");
       }
    }
}
