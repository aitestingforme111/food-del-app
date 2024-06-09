package com.del.app.service;

import com.del.app.exceptions.RiderNotFoundException;
import com.del.app.exceptions.RiderRegistrationException;
import com.del.app.model.Restaurant;
import com.del.app.model.Rider;
import com.del.app.model.RiderStatus;
import com.del.app.repository.RiderRepository;
import com.del.app.strategy.rider.RiderAssignmentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class RiderService {

    private final RiderRepository riderRepository;
    private final RiderAssignmentStrategy riderAssignmentStrategy;

    @Autowired
    public RiderService(RiderRepository riderRepository,
                        RiderAssignmentStrategy riderAssignmentStrategy) {
        this.riderRepository = riderRepository;
        this.riderAssignmentStrategy = riderAssignmentStrategy;
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
        Long nearestRiderId = riderAssignmentStrategy.chooseRider(availableRiders, restaurant);

        // Update rider status to assigned (optimistic locking is recommended)
        if (nearestRiderId != null) {
            riderRepository.updateRiderStatus(nearestRiderId, RiderStatus.ASSIGNED);
        }

        return nearestRiderId;
    }


    public void updateRiderLocation(Long riderId, Double latitude, Double longitude) throws RiderNotFoundException {
        Rider rider = getRiderById(riderId);
        rider.setLatitude(latitude);
        rider.setLongitude(longitude);
        riderRepository.save(rider);
    }

    public void setRiderAvailability(Long riderId, RiderStatus riderStatus) throws RiderNotFoundException {
        Rider rider = getRiderById(riderId);
        rider.setRiderStatus(riderStatus);
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
