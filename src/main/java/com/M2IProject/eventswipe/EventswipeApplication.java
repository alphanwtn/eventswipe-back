package com.M2IProject.eventswipe;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventswipeApplication implements CommandLineRunner {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(EventswipeApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

	}

}