package com.example.demo.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Rating;
import com.example.demo.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingInfo {
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallBackUserRating")
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingdata/users/"+userId, UserRating.class);
	}
	
	public UserRating getFallBackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserRatings(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}
}
