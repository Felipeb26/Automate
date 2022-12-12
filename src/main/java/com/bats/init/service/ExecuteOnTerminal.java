package com.bats.init.service;

import javafx.scene.control.ListView;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExecuteOnTerminal {

    public List<String> execs(String command, String path) {
        try {
            List<String> strings = new ArrayList<>();
            String lines;
            Process p = Runtime.getRuntime().exec(command, null, new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((lines=reader.readLine())!=null){
                strings.add(lines);
                System.out.println(lines);
            }
            return strings;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
