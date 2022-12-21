package com.bats.init.config;

import javafx.concurrent.Task;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilesUtils {

    private static final List<String> list = new ArrayList<>();
    private String line;

    public List<String> readFile(Class<?> classe) {
        try {
            var index = classe.getResourceAsStream("/css/index.css");
            if (index != null) {
                InputStreamReader reader = new InputStreamReader(index);
                BufferedReader br = new BufferedReader(reader);
                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
                closeProps(reader, br);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void closeProps(InputStreamReader reader, BufferedReader br) throws Exception {
        reader.close();
        br.close();
    }

}
