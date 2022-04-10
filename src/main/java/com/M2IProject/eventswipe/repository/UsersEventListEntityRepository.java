package com.M2IProject.eventswipe.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.M2IProject.eventswipe.model.UsersEventListEntity;

//This will be AUTO IMPLEMENTED by Spring into a Bean called UsersEventListEntityRepository
//CRUD refers Create, Read, Update, Delete

public interface UsersEventListEntityRepository extends CrudRepository<UsersEventListEntity, Integer> {

	Optional<UsersEventListEntity> findByUser(Integer user_id);
	
}
