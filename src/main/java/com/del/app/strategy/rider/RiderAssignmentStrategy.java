package com.del.app.strategy.rider;

import com.del.app.model.Restaurant;
import com.del.app.model.Rider;

import java.util.List;

public interface RiderAssignmentStrategy {
    Long chooseRider(List<Rider> availableRiders, Restaurant restaurant);
}