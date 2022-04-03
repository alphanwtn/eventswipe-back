package com.M2IProject.eventswipe.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.EventEntity;

@Service
public class EventEntityService {

	@Autowired
	private com.M2IProject.eventswipe.repository.EventEntityRepository eventEntityRepository;

	public List<EventEntity> getAllEventsByGenreName(List<String> searchedGenres) {

		List<EventEntity> eventsList = new ArrayList<>();

		for (String g : searchedGenres) {
			Iterable<EventEntity> resultByGenre = eventEntityRepository.findByGenreName(g);
			resultByGenre.forEach(x -> eventsList.add(x));
		}

		Collections.shuffle(eventsList);

		if (eventsList.size() > 30)
			return eventsList.subList(0, 30);
		// limitation du nombre d'event à 30 pour rapidité de test

		return eventsList;
	}

	public List<EventEntity> getAllEventsByGenreId(List<String> searchedGenresId) {

		List<EventEntity> eventsList = new ArrayList<>();

		for (String g : searchedGenresId) {
			Iterable<EventEntity> resultByGenre = eventEntityRepository.findAllByGenreId(g);
			resultByGenre.forEach(x -> eventsList.add(x));
		}

		Collections.shuffle(eventsList);

		if (eventsList.size() > 30)
			return eventsList.subList(0, 30);
		// limitation du nombre d'event à 30 pour rapidité de test

		return eventsList;
	}
}
