package com.M2IProject.eventswipe.model;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "events")

public class EventEntity {
	@Id
	private String id;

	@Column(length = 200)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(length = 200)
	private String url;

	@Temporal(TemporalType.TIMESTAMP) // ! GMT + 0
	private Calendar start_date_event;

	@Temporal(TemporalType.TIMESTAMP) // ! GMT + 0
	private Calendar start_date_sale;

	@Temporal(TemporalType.TIMESTAMP) // ! GMT + 0
	private Calendar end_date_sale;

	@OneToOne
	private SegmentEntity segment;

	@OneToOne
	private GenreEntity genre;

	@OneToOne()
	private SubGenreEntity subgenre;

	@OneToOne
	private VenueEntity venue;

	@ManyToMany
	private Set<AttractionEntity> attractions_in_event;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<ImageEntity> images_in_event;

}
