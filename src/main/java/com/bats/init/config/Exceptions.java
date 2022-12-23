package com.bats.init.config;

import lombok.experimental.UtilityClass;

import java.io.*;

@UtilityClass
public class Exceptions {

    private static final String path = System.getProperty("user.home");

    public void ToText(Exception e, PrintStream ps) {
        e.printStackTrace(ps);
    }

    public void ToText(Exception e) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/automate.log"));
            writer.write(String.format("Erro: %s\n\n\n", e.getMessage()));
            writer.flush();
            writer.close();
            PrintWriter pw = new PrintWriter(path+"file.log");
            e.printStackTrace(pw);
        } catch (Exception ex) {
            Exceptions.ToText(e);
        }
    }
}
