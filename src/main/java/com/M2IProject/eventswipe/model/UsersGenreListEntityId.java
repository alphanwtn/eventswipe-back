package com.M2IProject.eventswipe.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersGenreListEntityId implements Serializable {

	private static final long serialVersionUID = 541340847342855026L;

	@Column(name = "USER_ID")
	private int user_id;

	@Column(name = "GENRE_ID")
	private String genre_id;

}
