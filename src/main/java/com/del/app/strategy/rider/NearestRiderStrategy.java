package com.del.app.strategy.rider;

import com.del.app.model.Restaurant;
import com.del.app.model.Rider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NearestRiderStrategy implements RiderAssignmentStrategy {

    @Override
    public Long chooseRider(List<Rider> availableRiders, Restaurant restaurant) {
        if (availableRiders.isEmpty()) {
            return null;
        }

        Long nearestRiderId = null;
        double minDistance = Double.MAX_VALUE;
        for (Rider rider : availableRiders) {
            double distance = calculateDistance(restaurant.getLatitude(),
                    restaurant.getLongitude(),
                    rider.getLatitude(),
                    rider.getLongitude());
            System.out.println("Distance >>>>>>>>>>>>>>>>>>>>>>>>>>: " + distance);
            if (distance < minDistance) {
                minDistance = distance;
                nearestRiderId = rider.getId();
            }
        }
        return nearestRiderId;
    }

    private double calculateDistance(double restaurantLatitude,
                                     double restaurantLongitude,
                                     double riderLatitude,
                                     double riderLongitude) {
        final int earthRadius = 6371;

        double latDiff = Math.toRadians(riderLatitude - restaurantLatitude);
        double lonDiff = Math.toRadians(riderLongitude - restaurantLongitude);

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(restaurantLatitude)) * Math.cos(Math.toRadians(riderLatitude))
                        * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
}