package com.M2IProject.eventswipe.dto;

import com.M2IProject.eventswipe.model.EventEntity;
import com.M2IProject.eventswipe.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEventStatusDTO {

	private EventEntity event;
	private Status status;
}
