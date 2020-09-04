package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CatalogItem;
import com.example.demo.model.UserRating;
import com.example.demo.services.MovieInfo;
import com.example.demo.services.UserRatingInfo;

@RestController
public class MovieCatalogController {

	
	@Autowired
	public MovieInfo movieInfo;
	
	@Autowired
	public UserRatingInfo userRatingInfo;
	
	@GetMapping("/catalog/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		UserRating userRating = userRatingInfo.getUserRating(userId);
		
		return userRating.getUserRatings().stream().map(rating -> {
			//for each movie Id, call movie info service and get details
			return movieInfo.getCatalogItem(rating);
			}).collect(Collectors.toList());
	}
}
/*
Movie movie = webClient.buil()
.get()
.uri()
.retrieve()
.bodyToMono() mono is like a promise
.block
*/