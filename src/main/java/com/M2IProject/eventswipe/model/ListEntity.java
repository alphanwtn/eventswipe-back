package com.M2IProject.eventswipe.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "listevents")

public class ListEntity {

	@Id
	private String id;
	
	@ManyToMany
	private EventEntity event;
	
	@OneToOne
	private UserEntity user;
	
}
