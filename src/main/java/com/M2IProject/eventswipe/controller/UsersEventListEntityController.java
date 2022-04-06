package com.M2IProject.eventswipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.M2IProject.eventswipe.model.UsersEventListEntity;

@Controller
public class UsersEventListEntityController {
	@Autowired
	private com.M2IProject.eventswipe.service.UsersEventListEntityService UsersEventListEntityService;
	
	    // creating a get mapping that retrieves the detail of a specific userEventList
		@GetMapping("/eventlist/{userid}")
		private UsersEventListEntity getUserEvents(@PathVariable("userid") int userid) {
			return UsersEventListEntityService.getUsersEventListEntityByUserEntityId(userid);
        }
		
		// creating a delete mapping that deletes a specified event in the userEventList
		@DeleteMapping("/eventlist/{userid}/{eventid}")
		private UsersEventListEntity deleteUserEvent(@PathVariable("eventid") String eventid, @PathVariable("userid") int userid) {
			UsersEventListEntityService.deleteEventByEventId(eventid);
			return UsersEventListEntityService.getUsersEventListEntityByUserEntityId(userid);
		}
}