package com.M2IProject.eventswipe;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.M2IProject.eventswipe.service.scrapers.ClassificationScraper;

@SpringBootApplication
public class EventswipeApplication implements CommandLineRunner {

	public static void main(String[] args) throws IOException, InterruptedException {
		ApplicationContext applicationContext = SpringApplication.run(EventswipeApplication.class, args);

		Thread.sleep(10000); // tempo

		ClassificationScraper miniScraper = applicationContext.getBean(ClassificationScraper.class);
		miniScraper.run();

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}

}