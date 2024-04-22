package com.lcwd.user.service.external.services;

import com.lcwd.user.service.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// we will be calling Hotel Service using Feign client

// now we will be adding feign client interceptor whose role is to stop all feign client request
// and add a header into it and then execute the request forward
@FeignClient(name = "HOTEL-SERVICE") // mention the name of the service to be called
public interface HotelService {

    // provide unimplemented methods just like controller and annotating it inorder to make http api calls
    // it will run at runtime automatically by spring boot
    @GetMapping("/hotels/{hotelId}")
    Hotel getHotel(@PathVariable("hotelId") String hotelId);


}
