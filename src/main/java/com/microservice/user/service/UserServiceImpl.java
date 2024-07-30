package com.microservice.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.user.entity.User;
import com.microservice.user.exception.ResourceNotFoundException;
import com.microservice.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User saveuser(User user) {
		String randomuserId = UUID.randomUUID().toString();
		user.setUserId(randomuserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on server !!"+ userId));
	}

}
