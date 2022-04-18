package com.M2IProject.eventswipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.M2IProject.eventswipe.model.GenreEntity;
import com.M2IProject.eventswipe.service.GenreEntityService;

@Controller
@CrossOrigin(origins = "*")
//@RequestMapping(path = "/api")
public class GenreEntityController {

	@Autowired
	private GenreEntityService genreEntityService;

	@GetMapping(path = "/get-all-genres-by-segment-id")
	public @ResponseBody List<GenreEntity> getAllGenresBySegmentId(@RequestParam(value = "id") String segmentId) {
		return genreEntityService.getAllGenresBySegmentId(segmentId);
	}
}
