package com.M2IProject.eventswipe.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.M2IProject.eventswipe.model.EventEntity;
import com.M2IProject.eventswipe.model.Status;
import com.M2IProject.eventswipe.model.UserEntity;
import com.M2IProject.eventswipe.model.UsersEventListEntity;
import com.M2IProject.eventswipe.repository.EventEntityRepository;
import com.M2IProject.eventswipe.repository.UserEntityRepository;
import com.M2IProject.eventswipe.repository.UsersEventListEntityRepository;

@Service
public class UsersEventListEntityService {
	@Autowired
	UsersEventListEntityRepository usersEventListEntityRepository;
	@Autowired
	UserEntityRepository userEntityRepository;
	@Autowired
	EventEntityRepository eventEntityRepository;
	
	//getting all users record by using the method findaAll() of CrudRepository disregarding the status
		public List<EventEntity> getAllEventList(int id) {
			return eventEntityRepository.getAllEventList(id);
		}
	
    //getting all user's events that he liked
	public List<EventEntity> getAllEventListByStatus(int userid, String status) {
		return eventEntityRepository.getAllEventListByStatus1(userid, status);
	}
	// saving a specific record by using the method save() of CrudRepository
	public void addevent(int userId, String eventId, Status status) {
		UsersEventListEntity userEventList = new UsersEventListEntity();

		UserEntity user = userEntityRepository.findById(userId).get();
		EventEntity event = eventEntityRepository.findById(eventId).get();

		userEventList.setEvent(event);
		userEventList.setUser(user);
		userEventList.setStatus(status);
		usersEventListEntityRepository.save(userEventList);
	}

	//deleting an event from an user's eventList and returning the modified eventlist
	public List<EventEntity> deleteEventByEventId (int userId, String eventId) {
		eventEntityRepository.deleteUsersEventListByEventId(userId, eventId);
		return eventEntityRepository.getAllEventList(userId);

	}
}
