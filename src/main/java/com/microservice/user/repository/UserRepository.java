package com.microservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.user.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

}
