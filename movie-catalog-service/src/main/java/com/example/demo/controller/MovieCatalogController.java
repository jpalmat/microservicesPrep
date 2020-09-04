package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CatalogItem;
import com.example.demo.model.Movie;
import com.example.demo.model.Rating;
import com.example.demo.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class MovieCatalogController {
	
	//will  be deprecated
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/catalog/{userId}")
	@HystrixCommand(fallbackMethod = "getFallBackCatalog")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		UserRating userRating = getUserRating(userId);
		
		return userRating.getUserRatings().stream().map(rating -> {
			//for each movie Id, call movie info service and get details
			return getCatalogItem(rating);
			}).collect(Collectors.toList());
	}

	@HystrixCommand(fallbackMethod = "getFallBackCatalogItem")
	private CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movie/"+rating.getMovieId(), Movie.class);
		
		//put the all together
		return new CatalogItem(movie.getName(), "as", rating.getRating());
	}
	
	private CatalogItem getFallBackCatalogItem(Rating rating) {
		return new CatalogItem("No name", "as", rating.getRating());
	}

	@HystrixCommand(fallbackMethod = "getFallBackUserRating")
	private UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingdata/users/"+userId, UserRating.class);
	}
	
	private UserRating getFallBackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserRatings(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}

	public List<CatalogItem> getFallBackCatalog(@PathVariable("userId") String userId){
		
		return Arrays.asList(new CatalogItem("No movie", "", 0));
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