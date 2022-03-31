package com.M2IProject.eventswipe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.M2IProject.eventswipe.model.UserEntity;
import com.M2IProject.eventswipe.repository.UserEntityRepository;

@Service
public class UserEntityService {
	@Autowired
	UserEntityRepository UserEntityRepository;
	
	//getting all users record by using the method findaAll() of CrudRepository 
	public List<UserEntity> getAllUserEntity() {
		List<UserEntity> users = new ArrayList<UserEntity>();  
		UserEntityRepository.findAll().forEach(user -> users.add(user));  
		return users; 
	}

	//getting a specific record by using the method findById() of CrudRepository  
	public UserEntity getUserEntityById(int userid) {
		
		return UserEntityRepository.findById(userid).get();
	}

	//saving a specific record by using the method save() of CrudRepository  
	public void saveOrUpdate(UserEntity user) {
		UserEntityRepository.save(user);
		
	}

	//deleting a specific record by using the method deleteById() of CrudRepository     
	public void delete(int id) {
		UserEntityRepository.deleteById(id);
			
	}
		
	//updating a record  
	public void update(UserEntity user, int userid)   
	{  
		UserEntityRepository.save(user);  
	} 
}
