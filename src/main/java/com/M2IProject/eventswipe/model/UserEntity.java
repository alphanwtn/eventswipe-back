package com.M2IProject.eventswipe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
    
	@Column(length = 200)
	private String last_name;
	
	@Column(length = 200)
	private String first_name;
	
	@Column(length = 200)
	private String email;
	
	@Column(length = 200)
	private String city;
	
	@Column(length = 200)
	private String password;
	
	@ManyToMany
	private SegmentEntity segment;
	
	@ManyToMany
	private GenreEntity genre;
	
	@OneToOne
	private ListEntity list;

}
