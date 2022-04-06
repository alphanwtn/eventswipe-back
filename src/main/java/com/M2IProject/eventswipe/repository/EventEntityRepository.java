package com.M2IProject.eventswipe.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.M2IProject.eventswipe.model.EventEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called UserEntityRepository
// CRUD refers Create, Read, Update, Delete

public interface EventEntityRepository extends CrudRepository<EventEntity, String> {

	Iterable<EventEntity> findBySubgenreName(String string);

	Iterable<EventEntity> findByGenreName(String string);

	Iterable<EventEntity> findAllByGenreName(String g);

	Iterable<EventEntity> findAllByGenreId(String g);

	Optional<EventEntity> findById(String id);

}