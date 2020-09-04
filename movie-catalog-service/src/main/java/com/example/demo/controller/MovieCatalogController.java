package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CatalogItem;
import com.example.demo.model.Movie;
import com.example.demo.model.UserRating;

@RestController
public class MovieCatalogController {
	
	//will  be deprecated
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/catalog/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		UserRating userRating = restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingdata/users/"+userId, UserRating.class);
		
		return userRating.getUserRatings().stream().map(rating -> {
			//for each movie Id, call movie info service and get details
			Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movie/"+rating.getMovieId(), Movie.class);
			/*
			 Movie movie = webClient.buil()
			 .get()
			 .uri()
			 .retrieve()
			 .bodyToMono() mono is like a promise
			 .block
			 */
			//put the all together
			return new CatalogItem(movie.getName(), "as", rating.getRating());
			}).collect(Collectors.toList());
	}

}
