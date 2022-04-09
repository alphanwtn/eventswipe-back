package com.M2IProject.eventswipe.repository;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.M2IProject.eventswipe.model.EventEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called UserEntityRepository
// CRUD refers Create, Read, Update, Delete

public interface EventEntityRepository extends CrudRepository<EventEntity, String>  {
	
	Iterable<EventEntity> findBySubgenreName(String string);

	Iterable<EventEntity> findByGenreName(String string);

	Iterable<EventEntity> findAllByGenreName(String g);

	Iterable<EventEntity> findAllByGenreId(String g);

	Optional<EventEntity> findById(String id);
	
	@Query(value="select events.id,description,end_date_sale,name,start_date_event,start_date_sale,url,genre_id,segment_id,subgenre_id,venue_id from users_eventlist LEFT JOIN events ON users_eventlist.event_id = events.id WHERE users_eventlist.user_id = :id", nativeQuery = true)
	public List<EventEntity> getAllEventList(int id);
	
	@Query(value="select events.id,description,end_date_sale,name,start_date_event,start_date_sale,url,genre_id,segment_id,subgenre_id,venue_id from users_eventlist LEFT JOIN events ON users_eventlist.event_id = events.id WHERE users_eventlist.user_id = :userid AND users_eventlist.status = :status", nativeQuery = true)
	public List<EventEntity> getAllEventListByStatus1(int userid, String status);
	
	@Transactional
	@Modifying(clearAutomatically=true, flushAutomatically = true)
	@Query(value="DELETE FROM users_eventlist WHERE event_id = :eventid AND user_id = :userid", nativeQuery = true)
	public void deleteUsersEventListByEventId(int userid, String eventid);
}