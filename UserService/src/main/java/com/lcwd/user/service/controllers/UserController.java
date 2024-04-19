package com.lcwd.user.service.controllers;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import com.lcwd.user.service.services.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    // create
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    // get single user(userservice will call rating service to get rating details and then call hotelservice to get hotel details)
    // we have used @CircuitBreaker(name = "", fallback = "") since we are calling ratingservice and hotelservice from userservice
    // if any service is down then this circuit breaker will run the fallback method mentioned
    // circuitbreaker is used to let us know which service is up and which is not
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")  // if any other dependent service is down then call ratingHotelFallback method(circuit breaker)
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // creating fallback method for circuitbreaker(parameter and return type shold be same as in the method where @CircuitBreaker is annotated)
    // note: mention return type same as mentioned in the method where circuit breaker is implemented. i.e ex- getSingleUser()
    // the below fallback method will only run when any service(rating or hotel) is down
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        logger.info("Fallback is executed because some service is down", ex.getMessage());
        User user = User.builder()
                        .email("dummy@gmail.com")
                        .name("Dummy")
                        .about("This user is created dummy because some service is down")
                        .userId("12345")
                        .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // get all users
    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
