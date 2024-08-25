package com.microservice.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.entity.User;
import com.microservice.user.service.UserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@RequestMapping("/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userservice;

	@PostMapping("/addUser")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User user1 = userservice.saveuser(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}

	int retryCount = 1;
	
	@GetMapping("/getUser/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallBackMethod")
//	@Retry(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBackMethod")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBackMethod")
	public ResponseEntity<User> getSingleUser(@PathVariable("userId") String userId) {
		logger.info("Retry Count :{}", retryCount);
		retryCount++;
		User user = userservice.getUser(userId);
		return ResponseEntity.ok(user);

	}

	
	
	public ResponseEntity<User> ratingHotelFallBackMethod(String userId, Exception ex) {
	//	logger.info("Fallback is executed because service is down", ex.getMessage());
		
		
		User user = User.builder().email("gaurav.nishant1308@gmail.com").name("Nishant")
				.about("The user is created dummy because service is down").userId("12345").build();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> list = userservice.getAllUser();

		return ResponseEntity.ok(list);
	}
}
