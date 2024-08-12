package com.microservice.user.externalservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.microservice.user.entity.Rating;

@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

	@PostMapping("/rating/addRating")
	Rating create(Rating rating);
	
	
}
