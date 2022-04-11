package com.M2IProject.eventswipe.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersGenreListEntityId implements Serializable {

	@NotNull
	@Column(name = "USER_ID")
	private int user_id;

	@NotNull
	@Column(name = "GENRE_ID")
	private String genre_id;

}
