package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Rating;
import com.example.demo.model.UserRating;

@RestController
@RequestMapping("/ratingdata")
public class RatingController {

	@GetMapping("/{movieId}")
	public Rating getRating(String movieId) {
		return new Rating("id1", 2);
	}
	
	@GetMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String usderId){
		UserRating userrating = new UserRating();
		userrating.setUserRatings(Arrays.asList(new Rating("1234", 4),
				new Rating("5678", 3)));
	return userrating;
	}
}
