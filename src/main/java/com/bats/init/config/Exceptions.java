package com.bats.init.config;

import lombok.experimental.UtilityClass;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintStream;

@UtilityClass
public class Exceptions {

    private static final String path = System.getProperty("user.dir");

    public void ToText(Exception e, PrintStream ps) {
        e.printStackTrace(ps);
    }

    public void ToText(Exception e) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/automate.log"));
            writer.write(String.format("Erro: %s\n\n\n", e.getMessage()));
            writer.flush();
            writer.close();
            e.printStackTrace();
        } catch (Exception ex) {
            Exceptions.ToText(e);
        }
    }
}
