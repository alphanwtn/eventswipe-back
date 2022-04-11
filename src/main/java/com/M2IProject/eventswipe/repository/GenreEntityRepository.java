package com.M2IProject.eventswipe.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.M2IProject.eventswipe.model.GenreEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called UserEntityRepository
// CRUD refers Create, Read, Update, Delete

public interface GenreEntityRepository extends CrudRepository<GenreEntity, Integer> {

	Iterable<GenreEntity> findAllById(String string);

	Iterable<GenreEntity> findByInheritedsegmentId(String segmentId);

	GenreEntity findById(String id);

	@Query(value = "select genres.id,name,inheritedsegment_id,users_genrelist.user_id from users_genrelist LEFT JOIN genres ON users_genrelist.genre_id = genres.id WHERE users_genrelist.user_id = :userid", nativeQuery = true)
	public List<GenreEntity> getAllGenreList(int userid);

	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(value = "DELETE FROM users_genrelist WHERE users_genrelist.genre_id = :genreId AND user_id = :userId", nativeQuery = true)
	public void deleteUsersGenreListByGenreId(int userId, String genreId);

}