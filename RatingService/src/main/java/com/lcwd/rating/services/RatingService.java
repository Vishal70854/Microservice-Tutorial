package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {
    // create
    Rating create(Rating rating);

    // get all ratings
    List<Rating> getRatings();
    // get all ratings by userId
    List<Rating> getRatingByUserId(String userId);
    // get all ratings by hotel
    List<Rating> getRatingByHotelId(String hotelId);
}
