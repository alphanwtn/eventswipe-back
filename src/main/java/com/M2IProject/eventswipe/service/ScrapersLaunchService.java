package com.M2IProject.eventswipe.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.service.scrapers.ClassificationScraper;
import com.M2IProject.eventswipe.service.scrapers.EventScraper;

/**
 * 
 * Initialize les scrapers après le demarrage de l'appli
 *
 */
@Service
public class ScrapersLaunchService {

	@Autowired
	private ClassificationScraper clScraper;

	@Autowired
	private EventScraper evScraper;

	/**
	 * Lance les scrapers une fois que l'application est lancée
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void runClassificationScraper() throws IOException, InterruptedException {
		clScraper.run();
		evScraper.run();
	}

}
