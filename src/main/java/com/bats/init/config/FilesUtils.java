package com.bats.init.config;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilesUtils {

    private static final Format format = new Format();
    private static final String path = System.getProperty("user.home");
    private static final List<String> list = new ArrayList<>();
    private String line;

    private static String backHover;
    private static String textHover;
    private static String borderHover;


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

    public void writeFile(String backColor, String textColor, String borderColor) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/uimodel.css"));
            backHover = format.invertColors(backColor);
            textHover = format.invertColors(textColor);
            borderHover = format.invertColors(borderColor);

            writer.write("list-cell {-fx-text-fill: black;-fx-font-weight: bold;}\n\n");
            writer.write(".list-cell:filled:selected:focused,.list-cell:filled:selected " +
                    "{-fx-background-color: linear-gradient(#328BDB 0%, #207BCF 25%, #1973C9 75%, #0A65BF 100%);}\n\n");
            writer.write(".list-cell:filled:hover {-fx-background-color: #0093ff;}\n\n");

            writer.write(String.format(".root {-fx-background-color: rgba(%s);-fx-background-radius: 10;-fx-border-radius: 10;}\n\n", backColor));

            writer.write(String.format(".label {-fx-text-fill: rgba(%s);-fx-font-weight: bold;}\n\n", textColor));

            writer.write(String.format(".button {-fx-background-color: rgba(%s);-fx-text-fill: rgba(%s);-fx-border-color: rgba(%s);" +
                            "-fx-font-weight: bold;-fx-underline: true;-fx-border-width: 2;-fx-background-radius: 10;-fx-border-radius: 2;}\n\n",
                    backColor, textColor, borderColor));

            writer.write(String.format(".button:hover {-fx-background-color: rgb(%s);-fx-text-fill: rgba(%s);-fx-border-color: rgba(%s);}\n\n",
                    backHover, textHover, borderHover));

            writer.write(".root>#loads {-fx-background-image: url('../image/load.gif');-fx-background-size: contain;" +
                    "-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 100% 100%;}\n\n");

            writer.write("#Image {-fx-background-image: url('../image/all.png');-fx-background-size: contain;" +
                    "-fx-background-repeat: no-repeat;-fx-background-position: center;-fx-background-size: 100% 100%;}\n\n");

            writer.write(String.format("#hbox{-fx-border-color: rgba(%s);-fx-border-style: hidden hidden solid hidden;-fx-font-weight: bold;" +
                    "-fx-underline: true;-fx-border-width: 2;-fx-background-radius: 10;-fx-border-radius: 2;}\n\n", borderColor));

            writer.write("#lblBack, #lblText, #lblBorder{-fx-background-radius: 10;}\n\n");

            writer.flush();
            writer.close();
        } catch (Exception e) {
            Exceptions.ToText(e);
        }
    }

    public boolean deleteFile() {
        try {
            File file = new File(path + "/uimodel.css");
            if (file.exists()) {

                file.delete();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
