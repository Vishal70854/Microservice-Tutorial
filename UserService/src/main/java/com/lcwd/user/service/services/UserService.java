package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;

import java.util.List;

public interface UserService {

    // user operations

    // create
    User saveUser(User user);

    // get all users
    List<User> getAllUser();

    // get a user with userId
    User getUser(String userId);

//    TODO: delete
//    TODO: update
}
