package com.del.app.observer;

import org.springframework.stereotype.Service;

@Service
public class DefaultDeliveryTimeCalculator implements DeliveryTimeCalculator {

  @Override
  public Long calculateDeliveryTime(double restaurantLatitude, double restaurantLongitude, double customerLatitude, double customerLongitude) {
    Double distance = calculateDistance(restaurantLatitude, restaurantLongitude, customerLatitude, customerLongitude);
    if (distance != null) {
      // Calculate estimated time based on distance and average speed
      double estimatedTimeInMinutes = distance / AVERAGE_SPEED_KM_PER_MINUTE;
      return (long) Math.ceil(estimatedTimeInMinutes);
    } else {
      return null;
    }
  }

  private static final double AVERAGE_SPEED_KM_PER_MINUTE = 0.02;

  private Double calculateDistance(double restaurantLatitude, double restaurantLongitude, double customerLatitude, double customerLongitude) {
    final int earthRadius = 6371;

    double latDiff = Math.toRadians(customerLatitude - restaurantLatitude);
    double lonDiff = Math.toRadians(customerLongitude - restaurantLongitude);

    double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
            Math.cos(Math.toRadians(restaurantLatitude)) * Math.cos(Math.toRadians(customerLatitude))
                    * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return earthRadius * c;
  }
}