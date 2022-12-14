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

    //    private static final String path = System.getProperty("user.home");
    private static String os = System.getProperty("os.name");

    public List<String> execs(String command, String path, PrintStream ps) {
        os = os.toLowerCase();
        if (os.contains("windows")) {
            return powershell(command, path, ps);
        } else if (os.contains("linux")) {
            return linux(command, path, ps);
        } else {
            return null;
        }
    }

    public List<String> powershell(String command, String path, PrintStream ps) {
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

    public List<String> linux(String command, String path, PrintStream ps) {
        try {
            List<String> strings = new ArrayList<>();
            String lines;
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
}