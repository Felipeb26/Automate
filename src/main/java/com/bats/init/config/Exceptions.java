package com.bats.init.config;

import lombok.experimental.UtilityClass;

import java.io.PrintStream;

@UtilityClass
public class Exceptions {

    public void ToText(Exception e, PrintStream ps){
        e.printStackTrace(ps);
    }

}
