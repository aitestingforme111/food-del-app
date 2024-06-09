package com.del.app.service;

import com.del.app.model.Order;
import com.del.app.model.OrderItem;
import com.del.app.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderItemService {

  private final OrderItemRepository orderItemRepository;

  @Autowired
  public OrderItemService(OrderItemRepository orderItemRepository) {
    this.orderItemRepository = orderItemRepository;
  }

  public List<OrderItem> saveOrderItems(Order order, List<OrderItem> orderItems) {
    for (OrderItem item : orderItems) {
      item.setOrder(order);
      orderItemRepository.save(item);
    }
    return orderItems;
  }

  public List<OrderItem> findOrderItemsByOrder(Order order) {
    return orderItemRepository.findByOrder(order);
  }
}