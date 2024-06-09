package com.del.app.observer;

import com.del.app.model.Order;

public interface OrderObserver {

  void update(Order order);
}