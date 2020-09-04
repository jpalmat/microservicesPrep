package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CatalogItem;
import com.example.demo.model.Movie;
import com.example.demo.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfo {
	
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallBackCatalogItem", 
			commandProperties = {
					@HystrixProperty(
				            name="execution.isolation.thread.timeoutInMilliseconds",
				            value="500"),
				            @HystrixProperty(
				                name="circuitBreaker.requestVolumeThreshold",
				                value="30"),
				            @HystrixProperty(
				                name="circuitBreaker.errorThresholdPercentage",
				                value="25"),
				            @HystrixProperty(
				                name="metrics.rollingStats.timeInMilliseconds",
				                value="20000"),
				            @HystrixProperty(
				                name="circuitBreaker.sleepWindowInMilliseconds",
				                value="60000")
			})
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movie/"+rating.getMovieId(), Movie.class);
		
		//put the all together
		return new CatalogItem(movie.getName(), "as", rating.getRating());
	}
	
	public CatalogItem getFallBackCatalogItem(Rating rating) {
		return new CatalogItem("No name", "as", rating.getRating());
	}
}
