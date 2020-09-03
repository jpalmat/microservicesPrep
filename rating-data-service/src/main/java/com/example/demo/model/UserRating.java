package com.example.demo.model;

import java.util.List;

public class UserRating {
	
	public UserRating() {
		super();
	}

	private List<Rating> userRatings;

	public List<Rating> getUserRatings() {
		return userRatings;
	}

	public void setUserRatings(List<Rating> userRatings) {
		this.userRatings = userRatings;
	}

}
