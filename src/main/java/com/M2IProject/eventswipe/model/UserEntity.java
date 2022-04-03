package com.M2IProject.eventswipe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

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
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

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
	
//	@ManyToMany
//	@JoinTable(
//			name = "users_eventlist", 
//			joinColumns = @JoinColumn(name = "user_id"), 
//			inverseJoinColumns = @JoinColumn(name = "event_id"))
//	private Set<EventEntity> eventlist;

//	@ManyToMany
//	@JoinTable(
//			name = "users_segmentlist", 
//			joinColumns = @JoinColumn(name = "user_id"), 
//			inverseJoinColumns = @JoinColumn(name = "segment_id"))
//	private Set<SegmentEntity> segmentlist;
//	
//	@ManyToMany
//	@JoinTable(
//			name = "users_genrelist", 
//			joinColumns = @JoinColumn(name = "user_id"), 
//			inverseJoinColumns = @JoinColumn(name = "genre_id"))
//	private Set<GenreEntity> genrelist;
//	
//	@ManyToMany
//  @JoinTable(
//			name = "users_subgenrelist", 
//			joinColumns = @JoinColumn(name = "user_id"), 
//			inverseJoinColumns = @JoinColumn(name = "subgenre_id"))
//	private Set<SubGenreEntity> subgenrelist;
//	


}
