package com.M2IProject.eventswipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.M2IProject.eventswipe.model.GenreEntity;
import com.M2IProject.eventswipe.service.GenreEntityService;
import com.M2IProject.eventswipe.service.UsersGenreListEntityService;

@Controller
@RequestMapping("/genrelists")
public class UsersGenreListEntityController {
	@Autowired
	UsersGenreListEntityService usersGenreListEntityService;
	@Autowired
	GenreEntityService genreEntityService;

	// creating a get mapping that show all genres chosen by an user
	@GetMapping("/{userid}")
	private @ResponseBody List<GenreEntity> getAllGenreList(@PathVariable("userid") int userid) {
		return usersGenreListEntityService.getAllGenreList(userid);
	}

	// creating a post mapping that add a genre to a specific user's userGenreList
	@PostMapping("/{userid}/{genreid}")
	private @ResponseBody void addUserGenre(@PathVariable("userid") int userId,
			@PathVariable("genreid") String genreId) {
		usersGenreListEntityService.addgenre(userId, genreId);
	}

	// creating a delete mapping that deletes a specified genre in an user's
	// userGenreList
	@DeleteMapping("/{userid}/{genreid}")
	private @ResponseBody void deleteUserGenre(@PathVariable("userid") int userId,
			@PathVariable("genreid") String genreId) {
		usersGenreListEntityService.deleteGenreByGenreId(userId, genreId);
	}

	// creating a post mapping that add a list of selected genres to a specific
	// user's userGenreList
	@PostMapping("/{userid}/add-all-selected-genres")
	private @ResponseBody void addAllSelectedGenres(@PathVariable("userid") int userId,
			@RequestParam(value = "genreId") List<String> selectedGenres) {
		usersGenreListEntityService.addAllSelectedGenres(userId, selectedGenres);
	}
}
