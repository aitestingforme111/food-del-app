package com.del.app.service;

import com.del.app.exceptions.RestaurantNotFoundException;
import com.del.app.exceptions.RestaurantValidationException;
import com.del.app.model.Restaurant;
import com.del.app.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;

  @Autowired
  public RestaurantService(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
  }

  public Restaurant createRestaurant(Restaurant restaurant) throws RestaurantValidationException {
    validateRestaurant(restaurant); // Validate restaurant data
    return restaurantRepository.save(restaurant);
  }

  public Restaurant getRestaurantByName(String name) throws RestaurantNotFoundException {
    return restaurantRepository.findByName(name)
            .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with name: " + name));
  }

  public List<Restaurant> findRestaurantsByCuisineType(String cuisineType) {
    return restaurantRepository.findByCuisineType(cuisineType);
  }

  private void validateRestaurant(Restaurant restaurant) throws RestaurantValidationException {
    if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
      throw new RestaurantValidationException("Restaurant name cannot be empty");
    }
  }
}