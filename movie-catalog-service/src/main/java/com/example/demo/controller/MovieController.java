package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Movie;

@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Value("${apikey}")
	private String apikey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
//	@GetMapping("/{movieId}")
//	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
//		restTemplate.getForObject("https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+apikey, responseType)
//	}
}
