package com.M2IProject.eventswipe.repository;

import org.springframework.data.repository.CrudRepository;

import com.M2IProject.eventswipe.model.EventEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called UserEntityRepository
// CRUD refers Create, Read, Update, Delete

public interface EventEntityRepository extends CrudRepository<EventEntity, Integer> {

	Iterable<EventEntity> findBySubgenreName(String string);

	Iterable<EventEntity> findByGenreName(String string);

	Iterable<EventEntity> findAllByGenreName(String g);

	Iterable<EventEntity> findAllByGenreId(String g);

}