package com.bats.init.service;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;

public class Console extends OutputStream {

    private TextArea textArea;

    public Console(TextArea console){
        this.textArea = console;
    }

    public void appendText(String value){
        Platform.runLater(() -> textArea.appendText(value));
    }

    @Override
    public void write(int b) throws IOException {
        appendText(String.valueOf((char)b));
    }
}
