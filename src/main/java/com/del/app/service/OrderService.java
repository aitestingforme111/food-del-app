package com.del.app.service;

import com.del.app.exceptions.RestaurantNotFoundException;
import com.del.app.exceptions.UserNotFoundException;
import com.del.app.model.*;
import com.del.app.observer.OrderSubject;
import com.del.app.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService; // Inject UserService
    private final RestaurantService restaurantService;
    private final OrderItemService orderItemService; // Inject OrderItemService
    private final OrderSubject orderSubject;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        UserService userService,
                        RestaurantService restaurantService,
                        OrderItemService orderItemService,
                        OrderSubject orderSubject) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.orderItemService = orderItemService;
        this.orderSubject = orderSubject;
    }

    public Order createOrder(Long userId, String restaurantName, List<OrderItem> orderItems)
            throws UserNotFoundException, RestaurantNotFoundException {
        User user = userService.getUserById(userId);
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.PLACED);
        order.setOrderTime(new Date());
        order = orderRepository.save(order);
        orderItemService.saveOrderItems(order, orderItems);
        return order;
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(newStatus);
        orderSubject.notifyObservers(order);
        return orderRepository.save(order);
    }

    public List<Order> findOrdersByUserAndStatus(User user, OrderStatus status) {
        return orderRepository.findByUserAndStatus(user, status);
    }

    public List<Order> findOrdersByRestaurantAndStatus(Restaurant restaurant, OrderStatus status) {
        return orderRepository.findByRestaurantAndStatus(restaurant, status);
    }

    public List<Order> findOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> findOrdersByRestaurant(Restaurant restaurant) {
        return orderRepository.findByRestaurant(restaurant);
    }
}