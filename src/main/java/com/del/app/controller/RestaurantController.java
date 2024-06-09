package com.del.app.controller;

import com.del.app.exceptions.MenuItemNotFoundException;
import com.del.app.exceptions.RestaurantNotFoundException;
import com.del.app.exceptions.RestaurantValidationException;
import com.del.app.model.MenuItem;
import com.del.app.model.Restaurant;
import com.del.app.service.MenuItemService;
import com.del.app.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

  private final RestaurantService restaurantService;
  private final MenuItemService menuItemService;

  @Autowired
  public RestaurantController(RestaurantService restaurantService,
                              MenuItemService menuItemService) {
    this.restaurantService = restaurantService;
    this.menuItemService = menuItemService;
  }

  @PostMapping("/createMenu/{restaurantId}")
  public ResponseEntity<MenuItem> createMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItem menuItem)
          throws RestaurantNotFoundException, MenuItemNotFoundException {
    menuItem.setRestaurant(null);
    MenuItem createdMenuItem = menuItemService.createMenuItem(menuItem);
    menuItemService.addMenuItemToRestaurant(createdMenuItem.getId(), restaurantId);
    return new ResponseEntity<>(createdMenuItem, HttpStatus.CREATED);
  }

  @PostMapping("/register")
  public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant,
                                                     @RequestParam(required = false) List<Long> menuItemIds) throws RestaurantValidationException, RestaurantValidationException, MenuItemNotFoundException, RestaurantNotFoundException {
    Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
    if (menuItemIds != null) {
      for (Long id : menuItemIds) {
        menuItemService.addMenuItemToRestaurant(id, createdRestaurant.getId()); // Use MenuItemService
      }
    }
    return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
  }

  @GetMapping("/search/{name}")
  public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String name) throws RestaurantNotFoundException {
    Restaurant restaurant = restaurantService.getRestaurantByName(name);
    return new ResponseEntity<>(restaurant, HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<List<Restaurant>> findRestaurantsByCuisineType(@RequestParam String cuisineType) {
    List<Restaurant> restaurants = restaurantService.findRestaurantsByCuisineType(cuisineType);
    return new ResponseEntity<>(restaurants, HttpStatus.OK);
  }
}