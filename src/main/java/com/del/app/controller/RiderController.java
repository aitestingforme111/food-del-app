package com.del.app.controller;

import com.del.app.exceptions.RiderNotFoundException;
import com.del.app.exceptions.RiderRegistrationException;
import com.del.app.model.Rider;
import com.del.app.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/riders")
public class RiderController {

  private final RiderService riderService;

  @Autowired
  public RiderController(RiderService riderService) {
    this.riderService = riderService;
  }

  @PostMapping("/register")
  public ResponseEntity<Rider> registerRider(@RequestBody Rider rider) throws RiderRegistrationException, RiderRegistrationException, NoSuchAlgorithmException {
    Rider registeredRider = riderService.registerRider(rider);
    return new ResponseEntity<>(registeredRider, HttpStatus.CREATED);
  }

  @PutMapping("/location/{riderId}")
  public ResponseEntity<Void> updateRiderLocation(@PathVariable Long riderId, @RequestParam Double latitude, 
                                                   @RequestParam Double longitude) throws RiderNotFoundException {
    riderService.updateRiderLocation(riderId, latitude, longitude);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/availability/{riderId}")
  public ResponseEntity<Void> setRiderAvailability(@PathVariable Long riderId, @RequestParam Boolean isAvailable) 
                                                    throws RiderNotFoundException {
    riderService.setRiderAvailability(riderId, isAvailable);
    return ResponseEntity.noContent().build();
  }
}