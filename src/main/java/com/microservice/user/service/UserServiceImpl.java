package com.microservice.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.user.entity.Hotel;
import com.microservice.user.entity.Rating;
import com.microservice.user.entity.User;
import com.microservice.user.exception.ResourceNotFoundException;
import com.microservice.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class); 
	
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
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on server !!"+ userId));
	
		Rating[] ratingforUser = restTemplate.getForObject("http://localhost:8083/rating/user/"+userId, Rating[].class);
		logger.info("forObject {}", ratingforUser);
		
		List<Rating> ratings = Arrays.stream(ratingforUser).toList();
		
		List<Rating> ratingList = ratings.stream().map(rating->{
			ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotel/getHotel/"+rating.getHotelId(), Hotel.class);
			Hotel hotel = forEntity.getBody();
			logger.info("Response Status Code {}", forEntity.getStatusCode());
			rating.setHotel(hotel);
			return rating;
			
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		
		return user;
	}

}
