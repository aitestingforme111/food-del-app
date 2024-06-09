package com.del.app.controller;

import com.del.app.dto.CreateOrderRequest;
import com.del.app.dto.UpdateOrderStatusRequest;
import com.del.app.exceptions.InvalidOrderStatusChangeException;
import com.del.app.exceptions.OrderNotFoundException;
import com.del.app.exceptions.RestaurantNotFoundException;
import com.del.app.exceptions.UserNotFoundException;
import com.del.app.model.Order;
import com.del.app.model.OrderStatus;
import com.del.app.model.Restaurant;
import com.del.app.model.User;
import com.del.app.service.OrderService;
import com.del.app.service.RestaurantService;
import com.del.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;
  private final UserService userService;
  private final RestaurantService restaurantService;

  @Autowired
  public OrderController(OrderService orderService, UserService userService, RestaurantService restaurantService) {
    this.orderService = orderService;
    this.userService = userService;
    this.restaurantService = restaurantService;
  }

  @PostMapping("/create")
  public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request)
                                                  throws UserNotFoundException, RestaurantNotFoundException {
    Order order = orderService.createOrder(request.getUserId(), request.getRestaurantName(), request.getOrderItems());
    return new ResponseEntity<>(order, HttpStatus.CREATED);
  }

  @PatchMapping("/{orderId}/status")
  public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest request)
          throws OrderNotFoundException, InvalidOrderStatusChangeException {
    OrderStatus newStatus = request.getNewStatus();
    Order order = orderService.updateOrderStatus(orderId, newStatus);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) throws UserNotFoundException {
    User user = userService.getUserById(userId);
    List<Order> orders = orderService.findOrdersByUser(user);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<Order>> getOrdersByRestaurant(@PathVariable String restaurantId) throws RestaurantNotFoundException {
    Restaurant restaurant = restaurantService.getRestaurantByName(restaurantId);
    List<Order> orders = orderService.findOrdersByRestaurant(restaurant);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }
}