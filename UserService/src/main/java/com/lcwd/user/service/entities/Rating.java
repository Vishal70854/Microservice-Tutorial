package com.lcwd.user.service.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private String ratingId;
    private String userId;  // this info we will be receiving from user microservice
    private String hotelId;
    private int rating;
    private String feedback;

    private Hotel hotel;    // this data we will get from HOTEL SERVICE using Http api calls
}
