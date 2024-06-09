package com.del.app.repository;

import com.del.app.model.Order;
import com.del.app.model.OrderStatus;
import com.del.app.model.Restaurant;
import com.del.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUser(User user);
  List<Order> findByRestaurant(Restaurant restaurant);
  List<Order> findByUserAndStatus(User user, OrderStatus status);
  List<Order> findByRestaurantAndStatus(Restaurant restaurant, OrderStatus status);
}