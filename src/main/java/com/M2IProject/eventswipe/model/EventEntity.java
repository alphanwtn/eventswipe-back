package com.M2IProject.eventswipe.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

	@OneToOne
	private SubGenreEntity subgenre;

	@OneToOne(cascade = CascadeType.ALL)
	private VenueEntity venue;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "events_attractions", joinColumns = @JoinColumn(name = "events_id"), inverseJoinColumns = @JoinColumn(name = "attractions_id"))
	private List<AttractionEntity> attractionsinevent;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "events_images", joinColumns = @JoinColumn(name = "events_id"), inverseJoinColumns = @JoinColumn(name = "images_id"))
	private List<ImageEntity> imagesinevent;

}
