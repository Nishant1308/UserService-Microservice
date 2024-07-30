package com.microservice.user.controller;

import java.util.List;

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

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userservice;
	
	@PostMapping("/addUser")
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1 = userservice.saveuser(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	@GetMapping("/getUser/{userId}")
	public ResponseEntity<User> getSingleUser(@PathVariable("userId")String userId){
		User user = userservice.getUser(userId);
		return ResponseEntity.ok(user);
		
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> list = userservice.getAllUser();
		
		return ResponseEntity.ok(list);
	}
}
