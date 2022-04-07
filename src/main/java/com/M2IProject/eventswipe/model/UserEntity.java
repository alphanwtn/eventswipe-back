package com.M2IProject.eventswipe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//lombok allow to not put getter and setter
@NoArgsConstructor
@AllArgsConstructor
//mark class as an Entity 
@Entity
//defining class name as Table name
@Table(name = "users")

public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(length = 30)
	private String pseudo;

	@Column(length = 200)
	private String email;

	@Column(length = 200)
	private String city;

	private String password;

	private boolean active;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private Set<RoleEntity> rolelist;

}
