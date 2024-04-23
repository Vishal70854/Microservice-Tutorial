package com.lcwd.rating.controllers;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    // create rating
    @PreAuthorize("hasAuthority('Admin')")  // this api will not be directly called. only admin should be able to create a hotel object via this api
    @PostMapping()
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }

    // get all ratings
    @GetMapping()
    public ResponseEntity<List<Rating>> getRatings(){
        return ResponseEntity.ok(ratingService.getRatings());
    }

    // here we have defined scopes because we dont want our api's to be accessed directly by anyone. only specific users are allowed to access limited api's
    // it is because of @EnableMethodSecurity(prePostEnabled = true)


    // get all ratings by user id
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")  // this api will not be directly called. it can only be called by users with scope internal or admin
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable  String userId){
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
    }

    // get all ratings by hotel id
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable  String hotelId){
        return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
    }
}
