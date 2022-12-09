package com.bats.init.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

@Service
public class OpenTerminal {

    private static final String os = System.getProperty("os.name").toLowerCase();

    private static final String cd = "cd";
    private static Robot robot;

//    public static void main(String[] args) {
//        System.out.println(os);
//        if (os.contains("windows")) {
//            openWindowsTerminal(os);
//        }else if(os.contains("linux")){
//            openLinuxTerminal();
//        }
//    }

    public void discoverSystem(String path) {
        System.out.println(os);
        if (os.contains("windows")) {
            openWindowsTerminal(path);
        } else if (os.contains("linux")) {
            openLinuxTerminal();
        }
    }

    public void openLinuxTerminal() {

    }

    public void openWindowsTerminal(String keys) {
        try {
            robot = new Robot();

            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_R);

            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_R);
           write(robot, "cmd");

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);

            write(robot, cd);
            write(robot, keys);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(Robot robot, String comand) {
        for (char c : comand.toCharArray()) {
            System.out.println(c+" CHar ");
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                System.out.println("Key code not found for character '" + c + "'");
            }
            robot.delay(100);
            robot.keyPress(keyCode);
            robot.delay(100);
            robot.keyRelease(keyCode);
            robot.delay(100);
        }
    }
}
