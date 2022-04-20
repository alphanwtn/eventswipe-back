package com.M2IProject.eventswipe.repository;

import org.springframework.data.repository.CrudRepository;

import com.M2IProject.eventswipe.model.SegmentEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called UserEntityRepository
// CRUD refers Create, Read, Update, Delete

public interface SegmentEntityRepository extends CrudRepository<SegmentEntity, Integer> {

    SegmentEntity findById(String id);

}