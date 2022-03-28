package com.M2IProject.eventswipe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.M2IProject.eventswipe.model.GenreEntity;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping(path = "/api")
public class GenreEntityController {
	@Autowired
	private com.M2IProject.eventswipe.repository.GenreEntityRepository genreEntityRepository;

	@GetMapping(path = "/get-all-genres-by-segment-id")
	public @ResponseBody List<GenreEntity> getAllGenre(@RequestParam(value = "id") String segmentId) {

		List<GenreEntity> genreList = new ArrayList<>();

		Iterable<GenreEntity> resultBySegment = genreEntityRepository.findByInheritedsegmentId(segmentId);
		resultBySegment.forEach(x -> genreList.add(x));

		return genreList;
	}

}