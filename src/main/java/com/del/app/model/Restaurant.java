package com.del.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String phoneNumber;
    private String address;
    private Double latitude;
    private Double longitude;
    private String cuisineType;
    private Double rating;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems;

}