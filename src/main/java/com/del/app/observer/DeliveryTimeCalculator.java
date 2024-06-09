package com.del.app.observer;

public interface DeliveryTimeCalculator {
  Long calculateDeliveryTime(double restaurantLatitude, double restaurantLongitude, double customerLatitude, double customerLongitude);
}