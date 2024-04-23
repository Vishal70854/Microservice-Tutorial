package com.lcwd.hotel.controllers;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    // here we have defined scopes because we dont want our api's to be accessed directly by anyone. only specific users are allowed to access limited api's
    // it is because of @EnableMethodSecurity(prePostEnabled = true)

    //create
    @PreAuthorize("hasAuthority('Admin')")  // this api will not be directly called. only admin should be able to create a hotel object via this api
    @PostMapping()
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        Hotel hotel1 = hotelService.create(hotel); // create hotel by calling hotelservice create method
        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(hotel1);

    }
    //getAll
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")  // this api will not be directly called. it can only be called by users with scope internal or admin
    @GetMapping()
    public ResponseEntity<List<Hotel>> getAll(){

        return ResponseEntity.ok(hotelService.getAll());
    }

    //get single
    @PreAuthorize("hasAuthority('SCOPE_internal')")  // this api will not be directly called. it can only be called by users with scope internal
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> createHotel(@PathVariable String hotelId){
        Hotel hotel1 = hotelService.get(hotelId); // create hotel by calling hotelservice create method
        return ResponseEntity.status(HttpStatus.OK)
                                .body(hotel1);

    }
}
