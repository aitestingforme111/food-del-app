package com.del.app.service;

import com.del.app.exceptions.MenuItemNotFoundException;
import com.del.app.exceptions.RestaurantNotFoundException;
import com.del.app.model.MenuItem;
import com.del.app.model.Restaurant;
import com.del.app.repository.MenuItemRepository;
import com.del.app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

  private final MenuItemRepository menuItemRepository;
  private final RestaurantRepository restaurantRepository;

  @Autowired
  public MenuItemService(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
    this.menuItemRepository = menuItemRepository;
  }

  public MenuItem createMenuItem(MenuItem menuItem) {
    return menuItemRepository.save(menuItem);
  }

  public List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId) {
    return menuItemRepository.findByRestaurantId(restaurantId);
  }

  public void addMenuItemToRestaurant(Long menuItemId, Long restaurantId) throws MenuItemNotFoundException, RestaurantNotFoundException {
    MenuItem menuItem = menuItemRepository.findById(menuItemId)
        .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found with id: " + menuItemId));
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
    if (restaurant == null) {
      throw new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId);
    }
    menuItem.setRestaurant(restaurant);
    menuItemRepository.save(menuItem);
  }
}