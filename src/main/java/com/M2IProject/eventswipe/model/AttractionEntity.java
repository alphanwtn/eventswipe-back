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

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "attractions")
public class AttractionEntity {
	@Id
	private String id;

	@Column(length = 200)
	private String name;

	@Column(length = 200)
	private String url;

	@OneToOne(optional = true)
	private SegmentEntity segment;

	@OneToOne(optional = true)
	private GenreEntity genre;

	@OneToOne(optional = true)
	private SubGenreEntity subgenre;

	@ManyToMany(mappedBy = "attractions_in_event")
	private Set<EventEntity> events;
}
