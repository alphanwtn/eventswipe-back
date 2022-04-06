package com.M2IProject.eventswipe.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.EventEntity;
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

	// getting a specific user eventList by using the method getById() of
	// CrudRepository
//	public UsersEventListEntity getUsersEventListEntityByUserEntityId(int user_id) {
//		return usersEventListEntityRepository.findById(user_id).get();
//	}

	// saving a specific record by using the method save() of CrudRepository
	public void addevent(int userId, String eventId) {
		UsersEventListEntity userEventList = new UsersEventListEntity();

		UserEntity user = userEntityRepository.findById(userId).get();
		EventEntity event = eventEntityRepository.findById(eventId).get();

		userEventList.setEvent(event);
		userEventList.setUser(user);
		usersEventListEntityRepository.save(userEventList);
	}

	// deleting a specific record by using the method deleteById() of CrudRepository
//	public List<EventEntity> deleteEventByEventId (String eventid) {
//		return usersEventListEntityRepository.deleteEventByEventId(eventid);
//
//	}

}
