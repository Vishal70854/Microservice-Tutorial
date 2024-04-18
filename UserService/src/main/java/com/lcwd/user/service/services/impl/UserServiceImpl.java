package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // bydefault we donot have any bean/annotation for RestTemplate in spring boot. we need to configure it
    // we will configure the bean with @Bean annotation in main class or in some configuration class

    @Autowired
    private RestTemplate restTemplate; // this is used inorder to call other microservice api or for some other http api calls

    // making a bean of HotelService interface where we have used Feign client so as to
    // call HotelService to get hotel details for each rating
    @Autowired
    private HotelService hotelService;

    // logger is used to monitor the behaviour, events, actions within the application
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        // implement RATING SERVICE CALL : USING REST TEMPLATE
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        /*
        the flow of getting data from different microservice will be ...
        we will get user details from user service
        from rating service we will get rating done by each user
        from hotel service we will get hotel details for each rating
         */

        // get user from database with the help of userRepository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server - " +userId));

        // fetch rating of the above user from RATING SERVICE with the below mentioned url as example
        // http://localhost:8083/ratings/users/ed3e5f09-1460-4295-9552-dcfaf966bebe     // /users/userId is there. written just for example
        // currently we will be using RestTemplate to call api of RATING SERVICE(FEIGN CLIENT) can also be used for calling api's

        // restTemplate.getForObject(url, ArrayListType.class to be mentioned) inorder to get data from other microservice or another http api calls
        // here we have replace localhost:8083(localhost:port) with service name register on eureka discovery server
        // because we want to make it dynamic. if in future host or port changes then it will be difficult to debug
        // to get it done. use @LoadBalanced where resttemplate bean is defined. then we will be able to use services name instead of localhost:port
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ", ratingsOfUser);  // get all rating object from Rating Service with the url given above

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();   //convert array to arraylist

        List<Rating> ratingList = ratings.stream().map(rating -> {
            // api call to get the hotel
            // we will use the link of hotel service to fetch hotel details using rest template
            // http://localhost:8082/hotels/24ce50da-6766-4f25-ba29-580633c8ce9b
            // we will call HOTEL SERVICE using RestTemplate to get details of hotel by hotelId
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);

            Hotel hotel = hotelService.getHotel(rating.getHotelId());  // get hotel object by using Feign client
//            logger.info("response status code : {}", forEntity.getStatusCode());

            // set the hotel to rating
            rating.setHotel(hotel);
            // return the rating
            return rating;
        }).collect(Collectors.toList());

        // set the ratings from RATING SERVICE in ratings field for User
        user.setRatings(ratingList);

        return user;
    }
}
