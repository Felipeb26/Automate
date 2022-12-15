package com.bats.init.service;

import com.bats.init.config.Format;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.PrintStream;

public class Background extends Task<String> {

    private static ExecuteOnTerminal execute;
    private static Format format;
    @FXML
    private ListView<String> listPath, listCommand;
    private PrintStream ps;
    private static final String finite = "finite";
    private static String values;

    public Background(ListView<String> listPath, ListView<String> listCommand, PrintStream print) {
        this.listPath = listPath;
        this.listCommand = listCommand;
        this.ps = print;
    }

    @Override
    protected String call() throws Exception {
        execute = new ExecuteOnTerminal();
        format = new Format();
        var list = format.toList(listPath);
        for (String value : list) {
            var command = format.toListOfCommands(listCommand);
            for (var exec : command) {
                var result = execute.execs(exec, value, ps);
                var num = value.lastIndexOf("/");
                if (num == -1) {
                    num = value.lastIndexOf("\\");
                }
                String pathName = value.substring(num + 1);
                values = String.format("Executando comando: %s\t na Pasta: %s\n", exec, pathName);
                updateMessage(values);
                if (!result.isEmpty()) {
                    for (var text : result) {
                        values = text + "\n";
                        Thread.sleep(150);
                        updateMessage(values);
                    }
                }
                values = String.format("Finalizano comando: %s\t na Pasta: %s\n\n", exec, pathName);
                updateMessage(values);
            }
        }
        values = finite;
        updateMessage(values);
        return values;
    }

}
