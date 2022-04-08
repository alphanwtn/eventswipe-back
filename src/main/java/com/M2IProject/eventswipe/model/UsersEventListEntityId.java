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
public class UsersEventListEntityId implements Serializable {

	@Column(name = "USER_ID")
	private int user_id;
	
	@Column(name = "EVENT_ID")
	private String event_id;

}
