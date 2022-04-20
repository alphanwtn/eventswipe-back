package com.M2IProject.eventswipe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.GenreEntity;
import com.M2IProject.eventswipe.repository.GenreEntityRepository;

@Service
public class GenreEntityService {

    @Autowired
    private GenreEntityRepository genreEntityRepository;

    public List<GenreEntity> getAllGenresBySegmentId(String segmentId) {

	List<GenreEntity> genreList = new ArrayList<>();

	Iterable<GenreEntity> resultBySegment = genreEntityRepository.findByInheritedsegmentId(segmentId);
	resultBySegment.forEach(x -> genreList.add(x));

	return genreList;
    }
}
