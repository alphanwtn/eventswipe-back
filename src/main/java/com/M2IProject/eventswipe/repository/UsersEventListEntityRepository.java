package com.M2IProject.eventswipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.M2IProject.eventswipe.model.UsersEventListEntity;

//This will be AUTO IMPLEMENTED by Spring into a Bean called UsersEventListEntityRepository
//CRUD refers Create, Read, Update, Delete

public interface UsersEventListEntityRepository extends CrudRepository<UsersEventListEntity, Integer> {

    Optional<UsersEventListEntity> findByUser(Integer user_id);

    List<UsersEventListEntity> findAllByUserId(int userid);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM users_eventlist WHERE event_id = :eventid AND user_id = :userid", nativeQuery = true)
    public void deleteUsersEventListByEventId(int userid, String eventid);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE users_eventlist SET status = \"DISLIKED\" WHERE event_id = :eventId AND user_id = :userId", nativeQuery = true)
    public void changeStatusToDisliked(int userId, String eventId);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE users_eventlist SET status = CASE WHEN status = \"ALERTED\" THEN \"LIKED\" WHEN status = \"LIKED\" THEN \"ALERTED\" else status END WHERE user_id = :userId AND event_id = :eventId", nativeQuery = true)
    public void switchLikedAndAlerted(int userId, String eventId);

}
