package com.M2IProject.eventswipe.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.M2IProject.eventswipe.model.UsersGenreListEntity;

//This will be AUTO IMPLEMENTED by Spring into a Bean called UsersGenreListEntityRepository
//CRUD refers Create, Read, Update, Delete

public interface UsersGenreListEntityRepository extends CrudRepository<UsersGenreListEntity, Integer> {

	Optional<UsersGenreListEntity> findByUser(int id);

}
