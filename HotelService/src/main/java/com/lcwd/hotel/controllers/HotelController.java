package com.lcwd.hotel.controllers;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    //create
    @PostMapping()
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        Hotel hotel1 = hotelService.create(hotel); // create hotel by calling hotelservice create method
        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(hotel1);

    }
    //getAll
    @GetMapping()
    public ResponseEntity<List<Hotel>> getAll(){
        return ResponseEntity.ok(hotelService.getAll());
    }
    //get single
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> createHotel(@PathVariable String hotelId){
        Hotel hotel1 = hotelService.get(hotelId); // create hotel by calling hotelservice create method
        return ResponseEntity.status(HttpStatus.OK)
                                .body(hotel1);

    }
}
