package com.del.app.service;

import com.del.app.exceptions.RiderNotFoundException;
import com.del.app.exceptions.RiderRegistrationException;
import com.del.app.model.Restaurant;
import com.del.app.model.Rider;
import com.del.app.model.RiderStatus;
import com.del.app.repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class RiderService {

    private final RiderRepository riderRepository;

    @Autowired
    public RiderService(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    public Rider registerRider(Rider rider) throws RiderRegistrationException, NoSuchAlgorithmException {
        // Validate rider data (e.g., email format)
        if (!isValidEmail(rider.getEmail())) {
            throw new RiderRegistrationException("Invalid email format");
        }
        // Check if rider with email already exists
        if (riderRepository.findByEmail(rider.getEmail()).isPresent()) {
            throw new RiderRegistrationException("Email already exists");
        }
        // Hash password before saving (security best practice)
        rider.setPassword(hashPassword(rider.getPassword()));
        return riderRepository.save(rider);
    }

    public Long assignRiderToOrder(Restaurant restaurant) {
        // Find available riders near the restaurant
        List<Rider> availableRiders = riderRepository.findAvailableRidersByLocation(RiderStatus.AVAILABLE, restaurant.getLatitude(), restaurant.getLongitude(), 10.5);

        // Choose the nearest rider (implement a suitable selection strategy)
        Long nearestRiderId = chooseNearestRider(availableRiders, restaurant);

        // Update rider status to assigned (optimistic locking is recommended)
        if (nearestRiderId != null) {
            riderRepository.updateRiderStatus(nearestRiderId, RiderStatus.ASSIGNED);
        }

        return nearestRiderId;
    }

    private Long chooseNearestRider(List<Rider> availableRiders, Restaurant restaurant) {
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
        final int earthRadius = 6371; // Earth's radius in kilometers (adjust if needed)

        double latDiff = Math.toRadians(riderLatitude - restaurantLatitude);
        double lonDiff = Math.toRadians(riderLongitude - restaurantLongitude);

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(restaurantLatitude)) * Math.cos(Math.toRadians(riderLatitude))
                        * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    public void updateRiderLocation(Long riderId, Double latitude, Double longitude) throws RiderNotFoundException {
        Rider rider = getRiderById(riderId);
        rider.setLatitude(latitude);
        rider.setLongitude(longitude);
        riderRepository.save(rider);
    }

    public void setRiderAvailability(Long riderId, Boolean isAvailable) throws RiderNotFoundException {
        Rider rider = getRiderById(riderId);
        rider.setRiderStatus(RiderStatus.AVAILABLE);
        riderRepository.save(rider);
    }

    public Rider getRiderById(Long riderId) throws RiderNotFoundException {
        return riderRepository.findById(riderId)
                .orElseThrow(() -> new RiderNotFoundException("Rider not found with id: " + riderId));
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        byte[] hashedBytes = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
