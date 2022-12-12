package com.bats.init.service;

import com.bats.init.config.Format;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.PrintStream;

public class Background extends Task<String> {

    private final ExecuteOnTerminal execute = new ExecuteOnTerminal();
    private final Format format = new Format();

    @FXML
    private ListView<String> listPath, listCommand;
    @FXML
    private TextArea console;
    private static String values;
    private PrintStream ps;

    public Background(ListView<String> listPath, ListView<String> listCommand, TextArea textArea, PrintStream print) {
        this.listPath = listPath;
        this.listCommand = listCommand;
        this.console = textArea;
        this.ps = print;
    }

    @Override
    protected String call() throws Exception {
        var list = format.toList(listPath);
        for (String value : list) {
            var command = format.toListOfCommands(listCommand);
            for (var exec : command) {
                var result = execute.execs(exec, value, ps);
                if (result.isEmpty()) {
                    System.out.println(result);
                } else {
                    for (var text : result) {
                        text = text + "\n";
                        Thread.sleep(150);
                        console.appendText(text);
                        values = text;
                        updateValue(values);
                    }
                }
            }
        }
        format.resetCommandList(listCommand);
        if (isRunning()) {
            cancel();
        }
        return values;
    }
}
