package com.M2IProject.eventswipe;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.M2IProject.eventswipe.service.scrapers.ClassificationScraper;
import com.M2IProject.eventswipe.service.scrapers.EventScraper;

@SpringBootApplication
public class EventswipeApplication implements CommandLineRunner {

	public static void main(String[] args) throws IOException, InterruptedException {
		ApplicationContext applicationContext = SpringApplication.run(EventswipeApplication.class, args);

		// Scrappers running
		ClassificationScraper classificationScr = applicationContext.getBean(ClassificationScraper.class);
		EventScraper eventScr = applicationContext.getBean(EventScraper.class);
		classificationScr.run();
		eventScr.run();

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}

}