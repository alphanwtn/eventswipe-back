package com.M2IProject.eventswipe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.M2IProject.eventswipe.model.ERole;
import com.M2IProject.eventswipe.model.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(ERole name);

}
