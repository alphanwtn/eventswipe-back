package com.M2IProject.eventswipe.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.M2IProject.eventswipe.model.UserEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called UserEntityRepository
// CRUD refers Create, Read, Update, Delete

public interface UserEntityRepository extends CrudRepository<UserEntity, Integer> {

	Optional<UserEntity> findByEmail(String email);

	Boolean existsByEmail(String email);

}