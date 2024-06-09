package com.del.app.observer;

import com.del.app.exceptions.RiderNotFoundException;
import com.del.app.model.*;
import com.del.app.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiderAssignmentObserver implements OrderObserver {

  private final RiderService riderService;
  private final DeliveryTimeCalculator deliveryTimeCalculator;

  @Autowired
  public RiderAssignmentObserver(RiderService riderService,
                                 DeliveryTimeCalculator deliveryTimeCalculator) {
    this.riderService = riderService;
    this.deliveryTimeCalculator = deliveryTimeCalculator;
  }

  @Override
  public void update(Order order) {
    if (order.getStatus() == OrderStatus.PREPARED) {
      try {
        Restaurant restaurant = order.getRestaurant();
        User user = order.getUser();

        Long assignedRiderId = riderService.assignRiderToOrder(restaurant);
        Rider rider = riderService.getRiderById(assignedRiderId);
        order.setRider(rider);

        Long estimatedDeliveryTime = deliveryTimeCalculator.calculateDeliveryTime(
                restaurant.getLatitude(),
                restaurant.getLongitude(),
                user.getLatitude(),
                user.getLongitude()
                );
        order.setEstimatedDeliveryTime(estimatedDeliveryTime);
      } catch (RiderNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}