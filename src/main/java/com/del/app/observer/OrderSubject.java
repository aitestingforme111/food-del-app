package com.del.app.observer;

import com.del.app.model.Order;

public interface OrderSubject {
  void registerObserver(OrderObserver observer);
  void notifyObservers(Order order);
}