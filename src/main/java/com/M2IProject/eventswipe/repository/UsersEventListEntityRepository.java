package com.M2IProject.eventswipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.M2IProject.eventswipe.model.EventEntity;
import com.M2IProject.eventswipe.model.UsersEventListEntity;

//This will be AUTO IMPLEMENTED by Spring into a Bean called UsersEventListEntityRepository
//CRUD refers Create, Read, Update, Delete

public interface UsersEventListEntityRepository extends CrudRepository<UsersEventListEntity, Integer> {

	@Query("SELECT userEvent.event FROM UsersEventListEntity as userEvent WHERE userEntity.user.id = :paramUserId") 
	public List<EventEntity> findEventByUserId(@Param("paramUserId") Integer userId);
	
	@Query("SELECT userEvent.event FROM UsersEventListEntity as userEvent WHERE eventEntity.event.id = :paramEventId AND userEntity.user.id = :paramUserId"  ) 
	public List<EventEntity> deleteEventByEventId(@Param("paramEventId") String eventId);
		
}
