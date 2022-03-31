package com.M2IProject.eventswipe.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "genres")
public class GenreEntity {
	@Id
	private String id;

	@Column(length = 50)
	private String name;

	@OneToOne
	private SegmentEntity inheritedsegment;

//	@ManyToMany(mappedBy = "genrelist")
//	private Set<UserEntity> users;

}
