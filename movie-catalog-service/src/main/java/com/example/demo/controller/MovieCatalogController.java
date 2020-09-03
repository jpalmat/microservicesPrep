package com.example.demo.controller;

import java.util.Arrays;
import java.util.Collections;
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

@RestController
public class MovieCatalogController {
	
	//will  be deprecated
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/catalog/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		List<Rating> ratings = Arrays.asList(new Rating("1234", 4),
				new Rating("5678", 3));
		
		return ratings.stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://localhost:8082/movie/"+rating.getMovieId(), Movie.class);
			/*
			 Movie movie = webClient.buil()
			 .get()
			 .uri()
			 .retrieve()
			 .bodyToMono() mono is like a promise
			 .block
			 */
			
			return new CatalogItem(movie.getName(), "as", rating.getRating());
			}).collect(Collectors.toList());
	}

}
