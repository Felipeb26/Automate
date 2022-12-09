package com.bats.init;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatsSpringApplication {

	public static void main(String[] args) {
//		System.setProperty("java.awt.headless", "true");
		Application.launch(JavaFxApplication.class);
	}

}
