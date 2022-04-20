package com.M2IProject.eventswipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
public class UsersGenreListEntityController {
    @Autowired
    UsersGenreListEntityService usersGenreListEntityService;
    @Autowired
    GenreEntityService genreEntityService;

    // creating a get mapping that show all genres chosen by an user
    @GetMapping("/{userid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userid")
    public @ResponseBody List<GenreEntity> getAllGenreList(@PathVariable("userid") int userid) {
	return usersGenreListEntityService.getAllGenreList(userid);
    }

    // creating a post mapping that add a genre to a specific user's userGenreList
    @PostMapping("/{userid}/{genreid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userid")
    public @ResponseBody void addUserGenre(@PathVariable("userid") int userid,
	    @PathVariable("genreid") String genreId) {
	usersGenreListEntityService.addgenre(userid, genreId);
    }

    // creating a delete mapping that deletes a specified genre in an user's
    // userGenreList
    @DeleteMapping("/{userid}/{genreid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userid")
    public @ResponseBody void deleteUserGenre(@PathVariable("userid") int userid,
	    @PathVariable("genreid") String genreId) {
	usersGenreListEntityService.deleteGenreByGenreId(userid, genreId);
    }

    // creating a post mapping that add a list of selected genres to a specific
    // user's userGenreList
    @PostMapping("/{userid}/add-all-selected-genres")
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userid")
    public @ResponseBody void addAllSelectedGenres(@PathVariable("userid") int userid,
	    @RequestParam(value = "genreId") List<String> selectedGenres) {
	usersGenreListEntityService.addAllSelectedGenres(userid, selectedGenres);
    }
}
