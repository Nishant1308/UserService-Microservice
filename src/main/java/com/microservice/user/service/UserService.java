package com.microservice.user.service;

import java.util.List;

import com.microservice.user.entity.User;

public interface UserService {
	//save user
	User saveuser(User user);
	
	//get all user
	List<User> getAllUser();
	
	//get user by userid
	User getUser(String userId);
}
