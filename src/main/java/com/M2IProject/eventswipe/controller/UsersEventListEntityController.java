package com.M2IProject.eventswipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.M2IProject.eventswipe.model.EventEntity;
import com.M2IProject.eventswipe.model.Status;
import com.M2IProject.eventswipe.service.UsersEventListEntityService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/eventlists")
public class UsersEventListEntityController {
    @Autowired
    UsersEventListEntityService usersEventListEntityService;

    // creating a get mapping that show all events for a specific user's
    // userEventList
    @GetMapping("/{userid}")
    private @ResponseBody List<EventEntity> getAllEventList(@PathVariable("userid") int userid) {
	return usersEventListEntityService.getAllEventList(userid);
    }

    // creating a get mapping that show all events liked by an user
    @GetMapping("/{userid}/{STATUS}")
    private @ResponseBody List<EventEntity> getAllEventListByStatus(@PathVariable("userid") int userid,
	    @PathVariable("STATUS") String status) {
	return usersEventListEntityService.getAllEventListByStatus(userid, status);
    }

    // creating a post mapping that add an event to a specific user's userEventList
    @PostMapping("/{userid}/{eventid}/{STATUS}")
    private @ResponseBody void addUserEvent(@PathVariable("userid") int userId, @PathVariable("eventid") String eventId,
	    @PathVariable("STATUS") Status status) {
	usersEventListEntityService.addevent(userId, eventId, status);

    }

    // creating a delete mapping that deletes a specified event in an user's
    // userEventList
    @DeleteMapping("/{userid}/{eventid}")
    private @ResponseBody void deleteUserEvent(@PathVariable("userid") int userId,
	    @PathVariable("eventid") String eventId) {
	usersEventListEntityService.deleteEventByEventId(userId, eventId);
    }

}