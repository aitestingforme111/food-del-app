package com.del.app.observer;

import com.del.app.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultOrderSubject implements OrderSubject {
  private final List<OrderObserver> observers = new ArrayList<>();

  @Override
  public void registerObserver(OrderObserver observer) {
    observers.add(observer);
  }

  @Override
  public void notifyObservers(Order order) {
    for (OrderObserver observer : observers) {
      observer.update(order);
    }
  }
}