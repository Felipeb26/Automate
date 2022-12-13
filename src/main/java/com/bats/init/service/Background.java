package com.bats.init.service;

import com.bats.init.config.Exceptions;
import com.bats.init.config.Format;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    private Label lblErro;

    public Background(ListView<String> listPath, ListView<String> listCommand, TextArea textArea, PrintStream print, Label label) {
        this.listPath = listPath;
        this.listCommand = listCommand;
        this.console = textArea;
        this.ps = print;
        this.lblErro = label;
    }

    @Override
    protected String call() {
        try {
            var list = format.toList(listPath);
            for (String value : list) {
                var command = format.toListOfCommands(listCommand);
                for (var exec : command) {
                    var result = execute.execs(exec, value, ps);
                    var num = value.lastIndexOf("/");
//                    if (num == -1) {
//                        num = value.lastIndexOf("\\");
//                    }
//                    value = value.substring(num);
                    String message = String.format("Executando comando: %s\t", exec);
                    console.appendText(message);
                    if (result.isEmpty()) {
                        System.out.println(result);
                    } else {
                        for (var text : result) {
                            values = text + "\n";
                            console.appendText(values);
                            updateValue(values);
                        }
                    }
                    console.appendText(message.replace("Executando", "Finalizando"));
                }
            }
            format.resetCommandList(listCommand);
            lblErro.setText("Finalizado todos comandos!");
        }catch (Exception e){
            Exceptions.ToText(e, ps);
        }
        return values;
    }

}
