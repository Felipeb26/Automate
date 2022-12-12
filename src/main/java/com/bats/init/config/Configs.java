package com.bats.init.config;

import javafx.scene.control.ListView;
import org.springframework.stereotype.Service;

@Service
public class Configs {

    public void List(ListView<String> listView) {
        var itens = listView.getItems().stream().count();
        if (itens > 5) {
            listView.getItems().remove(5);
        }
    }

}
