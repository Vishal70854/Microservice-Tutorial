package com.lcwd.user.service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "micro_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id // primary key annotation
    @Column(name = "ID")
    private String userId;
    @Column(name = "NAME", length = 20) // max length of name is 20
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ABOUT")
    private String about;

    @Transient  // it means we don't want Rating class to be stored in db as Rating will be another microservice we will be working on
    private List<Rating> ratings = new ArrayList<>();
}
