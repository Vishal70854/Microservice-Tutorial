package com.lcwd.user.service.repositories;

import com.lcwd.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // if you want to implement any custom query or method
    // you can write the query here
}
