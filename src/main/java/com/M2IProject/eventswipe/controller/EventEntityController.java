package com.M2IProject.eventswipe.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.M2IProject.eventswipe.model.EventEntity;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping(path = "/api")
public class EventEntityController {
	@Autowired
	private com.M2IProject.eventswipe.repository.EventEntityRepository eventEntityRepository;

	@GetMapping(path = "/events-by-genre")
	public @ResponseBody List<EventEntity> getAllEvents(@RequestParam(value = "genre") List<String> searchedGenres) {
		// This returns a JSON or XML with the users

		List<EventEntity> eventsList = new ArrayList<>();

		for (String g : searchedGenres) {
			Iterable<EventEntity> resultByGenre = eventEntityRepository.findByGenreName(g);
			resultByGenre.forEach(x -> eventsList.add(x));
		}

		Collections.shuffle(eventsList);

		eventsList.forEach(x -> System.out.println(x.getImages_in_event()));
		return eventsList;
	}

	// exemple fonctionnel
	@GetMapping(path = "/allEventByGenre")
	public @ResponseBody Iterable<EventEntity> getAllEvents2() {
		// This returns a JSON or XML with the users
		return eventEntityRepository.findByGenreName("Music");
	}
}