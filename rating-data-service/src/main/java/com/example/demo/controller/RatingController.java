package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Rating;

@RestController
public class RatingController {

	@GetMapping("/ratingdata/{movieId}")
	public Rating getRating(String movieId) {
		return new Rating("id1", 2);
	}
}
