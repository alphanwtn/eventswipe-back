package com.M2IProject.eventswipe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, unique = true)
    private ERole name;
}
