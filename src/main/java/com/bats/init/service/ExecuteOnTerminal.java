package com.bats.init.service;

import com.bats.init.config.Exceptions;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExecuteOnTerminal {
//    C:/Users/felipe.silva/AppData/Local/Programs/Git/bin/bash.exe -i -l

    private static final String path = System.getProperty("user.home");

    public List<String> execs(String command, String path, PrintStream ps) {
        try {
            List<String> strings = new ArrayList<>();
            String lines;
            command = String.format("powershell.exe %s", command);
            path = path + "/";
            Process p = Runtime.getRuntime().exec(command, null, new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((lines = reader.readLine()) != null) {
                strings.add(lines);
            }
            return strings;
        } catch (Exception e) {
            Exceptions.ToText(e, ps);
            return null;
        }
    }

    public List<String> execs(String terminal, String command, String path, PrintStream ps) {
        try {
            List<String> strings = new ArrayList<>();
            String lines;
            command = String.format("powershell.exe %s", command);
            path = path + "/";
            Process p = Runtime.getRuntime().exec(command, null, new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((lines = reader.readLine()) != null) {
                strings.add(lines);
//                System.out.println(lines);
            }
            return strings;
        } catch (Exception e) {
            Exceptions.ToText(e, ps);
            e.printStackTrace();
            return null;
        }
    }

    public static String checkTerminal(String terminal) {
        terminal = terminal.toLowerCase();
        if (terminal.isEmpty() || terminal.isBlank()) {
            return null;
        }
        if (terminal.equals("bash")) {
            return path + "AppData/Local/Programs/Git/bin/bash.exe -i -l";
        }
        if (terminal.equals("cmd")) {
            return "cmd.exe";
        }
        if (terminal.equals("powershell")) {
            return "powershell.exe";
        }
        return "powershell.exe";
    }

}