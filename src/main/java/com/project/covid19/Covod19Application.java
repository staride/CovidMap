package com.project.covid19;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class Covod19Application {
	public static void main(String[] args) {
		SpringApplication.run(Covod19Application.class, args);
	}

}
