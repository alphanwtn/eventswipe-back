package com.M2IProject.eventswipe.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.dto.UserEventStatusDTO;
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

	// getting all records for a user by using the method findaAll() of
	// CrudRepository disregarding the status
	public List<EventEntity> getAllEventList(int id) {
		return eventEntityRepository.getAllEventList(id);
	}

	// getting all user's events that he liked, disliked or alerted (by status)
	public List<EventEntity> getAllEventListByStatus(int userid, String status) {
		return eventEntityRepository.getAllEventListByStatus(userid, status);
	}

	// getting all user's events liked and alerted in one request
	public List<UserEventStatusDTO> getAllEventLikedAndAlerted(int userid) {
		List<UserEventStatusDTO> dtoList = new ArrayList<>();

		getAllEventListByStatus(userid, "LIKED").forEach(x -> {
			UserEventStatusDTO dto = new UserEventStatusDTO(x, Status.LIKED);
			dtoList.add(dto);
		});

		getAllEventListByStatus(userid, "ALERTED").forEach(x -> {
			UserEventStatusDTO dto = new UserEventStatusDTO(x, Status.ALERTED);
			dtoList.add(dto);
		});

		Collections.sort(dtoList);

		return dtoList;
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

	// deleting an event from an user's eventList and returning the modified
	// eventlist
	public void deleteUsersEventListByEventId(int userId, String eventId) {
		usersEventListEntityRepository.deleteUsersEventListByEventId(userId, eventId);
	}

	// modifying the status of an event in an user's eventlist here liked to
	// disliked
	public void changeStatusToDisliked(int userId, String eventId) {
		usersEventListEntityRepository.changeStatusToDisliked(userId, eventId);
	}

	// modifying the status of an event in a users's eventlist here liked to alerted
	// and vice versa
	public void switchLikedAndAlerted(int userId, String eventId) {
		usersEventListEntityRepository.switchLikedAndAlerted(userId, eventId);
	}
}
