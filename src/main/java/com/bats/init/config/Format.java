package com.bats.init.config;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class Format {
    private static double alpha = 0;

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
        var count = lista.stream().count();
        label.setText(String.format("%s diretorios listados!", count));
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
        if (!lista1.isEmpty() || !list2.isEmpty()) {
            listPath.getItems().clear();
            directoryName.getItems().clear();
        }
    }

    public void resetLabel(Label lblErro) {
        var lbl = lblErro.getText();
        if (!lbl.isEmpty() || !lbl.isBlank()) {
            lblErro.setText("");
        }
    }

    public String rgbFromColorPicker(Color color) {
        var red = color.getRed();
        var blue = color.getBlue();
        var green = color.getGreen();
        var alpha = color.getOpacity();
        return String.format("%s, %s,%s ,%s", (int) (red * 255), (int) (blue * 255),
                (int) (green * 255), (int) alpha);
    }

    public String invertColors(String colors) {
        var value = colors.split(",");
        var red = 255 - Integer.parseInt(value[0].trim());
        var green = 255 - Integer.parseInt(value[1].trim());
        var blue = 255 - Integer.parseInt(value[2].trim());
        if (value.length >= 4) {
            alpha = Double.parseDouble(value[3].trim());
        } else {
            alpha = 1;
        }
        red = limitRGB(red);
        green = limitRGB(green);
        blue = limitRGB(blue);

        var color = Color.rgb(red, green, blue);

        var b = color.getBlue();
        var r = color.getRed();
        var g = color.getGreen();
        return String.format("%s,%s,%s,%s", (int) (r * 255), (int) (g * 255), (int) (b * 255), alpha);
    }

    private int limitRGB(int n) {
        if (n > 255) {
            return 255;
        } else if (n < 0) {
            return 0;
        } else {
            return n;
        }
    }
}
