package com.del.app.observer;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DefaultDeliveryTimeCalculator implements DeliveryTimeCalculator {

  @Override
  public Long calculateDeliveryTime(double restaurantLatitude, double restaurantLongitude, double customerLatitude, double customerLongitude) {
    Double distance = calculateDistance(restaurantLatitude, restaurantLongitude, customerLatitude, customerLongitude);
    if (distance != null) {
      // Calculate estimated time based on distance and average speed
      double estimatedTimeInMinutes = distance / AVERAGE_SPEED_KM_PER_MINUTE;
      return TimeUnit.MINUTES.toMillis((long)Math.ceil(estimatedTimeInMinutes));
    } else {
      // Handle cases where distance calculation fails (optional)
      return null; // Or set a default value
    }
  }

  // Replace with your actual average speed based on your region and delivery type
  private static final double AVERAGE_SPEED_KM_PER_MINUTE = 0.02; // Adjust as needed (e.g., for car, bike)

  // Implement your distance calculation logic based on latitude and longitude
  private Double calculateDistance(double restaurantLatitude, double restaurantLongitude, double customerLatitude, double customerLongitude) {
    // You can use Haversine formula or other distance calculation methods
    // You can find libraries or online resources for distance calculation in Java

    // Replace this with your implementation
    return null;
  }
}