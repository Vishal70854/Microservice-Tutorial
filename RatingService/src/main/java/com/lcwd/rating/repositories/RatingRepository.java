package com.lcwd.rating.repositories;

import com.lcwd.rating.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// note : whenever working with NOSQL dbs like mongodb we have
//           to extends MongoRepository<ClassName, PrimaryKeyType> for mongodb and similarly for other nosql dbs

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    // custom finder methods
    //-------------------------------
    // its written in the following format
    // return type findBy(then mention the column name of Rating class in camelCase for ex : findByUserId(String userId // parameter))
    // findBy(columnName) will be dependent on the column name for the class which we are extending in repository class
    // in this way we write custom queries
    //---------------------------------
    // find all ratings by userId
    List<Rating> findByUserId(String userId);

    // find all ratings by hotelId
    List<Rating> findByHotelId(String hotelId);

}
