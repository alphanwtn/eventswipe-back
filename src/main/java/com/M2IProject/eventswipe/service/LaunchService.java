package com.M2IProject.eventswipe.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.ERole;
import com.M2IProject.eventswipe.model.RoleEntity;
import com.M2IProject.eventswipe.repository.RoleEntityRepository;
import com.M2IProject.eventswipe.service.scrapers.ClassificationScraper;
import com.M2IProject.eventswipe.service.scrapers.EventScraper;

/**
 * 
 * Start specific action at the starting on the app
 *
 */
@Service
public class LaunchService {

	@Autowired
	private ClassificationScraper classificationScraper;

	@Autowired
	private EventScraper eventScraper;

	@Autowired
	private RoleEntityRepository roleEntityRepository;

	/**
	 * Do the folling action when the app is ready : Check if the roles exists in
	 * DB, create them if not Start all the scrapers
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void runClassificationScraper() throws IOException, InterruptedException {

		if (roleEntityRepository.findByName(ERole.ROLE_ADMIN).isEmpty())
			roleEntityRepository.save(new RoleEntity(1, ERole.ROLE_ADMIN));

		if (roleEntityRepository.findByName(ERole.ROLE_USER).isEmpty())
			roleEntityRepository.save(new RoleEntity(2, ERole.ROLE_USER));

		classificationScraper.run();
		eventScraper.run();
	}
}
