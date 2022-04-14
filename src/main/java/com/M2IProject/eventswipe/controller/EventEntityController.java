package com.M2IProject.eventswipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.M2IProject.eventswipe.model.EventEntity;

@Controller

@CrossOrigin(origins = "*")
@RequestMapping("/events")

public class EventEntityController {

    @Autowired
    private com.M2IProject.eventswipe.service.EventEntityService eventEntityService;

    @GetMapping(path = "/get-events-by-genre-name")
    public @ResponseBody List<EventEntity> getAllEventsByName(
	    @RequestParam(value = "genre") List<String> searchedGenreNames) {

	return eventEntityService.getAllEventsByGenreName(searchedGenreNames);
    }

    @GetMapping(path = "/get-events-by-genre-id")
    public @ResponseBody List<EventEntity> getAllEventsByGenreId(
	    @RequestParam(value = "id") List<String> searchedGenreIds) {

	return eventEntityService.getAllEventsByGenreId(searchedGenreIds);
    }

    @GetMapping("/get-user-events-pull/{userid}")
    public @ResponseBody List<EventEntity> getUserEventsPull(@PathVariable("userid") int userid) {
	return eventEntityService.getUserEventsPull(userid);
    }

}