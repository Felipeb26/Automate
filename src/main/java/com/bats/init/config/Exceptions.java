package com.bats.init.config;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@UtilityClass
public class Exceptions {

    public void ToText(Exception e, PrintStream ps){
        e.printStackTrace(ps);
    }

}
