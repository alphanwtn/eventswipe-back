package com.M2IProject.eventswipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.GenreEntity;
import com.M2IProject.eventswipe.model.UserEntity;
import com.M2IProject.eventswipe.model.UsersGenreListEntity;
import com.M2IProject.eventswipe.repository.GenreEntityRepository;
import com.M2IProject.eventswipe.repository.UserEntityRepository;
import com.M2IProject.eventswipe.repository.UsersGenreListEntityRepository;

@Service
public class UsersGenreListEntityService {
    @Autowired
    UsersGenreListEntityRepository usersGenreListEntityRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    GenreEntityRepository genreEntityRepository;

    // get all genres selected by a specific user
    public List<GenreEntity> getAllGenreList(int userid) {
	return genreEntityRepository.getAllGenreList(userid);
    }

    // add a genre to a user's genreList
    public void addgenre(int userId, String genreId) {
	UsersGenreListEntity userGenreList = new UsersGenreListEntity();
	UserEntity user = userEntityRepository.findById(userId).get();
	GenreEntity genre = genreEntityRepository.findById(genreId);

	userGenreList.setUser(user);
	userGenreList.setGenre(genre);
	usersGenreListEntityRepository.save(userGenreList);
    }

    // add multiple genres to a user's genreList at the same time
    public void addAllSelectedGenres(int userId, List<String> selectedGenres) {
	for (String g : selectedGenres) {
	    Iterable<GenreEntity> genres = genreEntityRepository.findAllById(g);
	    genres.forEach(x -> addgenre(userId, x.getId()));
	}
    }

    // delete a genre from a user's genreList
    public List<GenreEntity> deleteGenreByGenreId(int userId, String genreId) {
	genreEntityRepository.deleteUsersGenreListByGenreId(userId, genreId);
	return genreEntityRepository.getAllGenreList(userId);
    }
}
