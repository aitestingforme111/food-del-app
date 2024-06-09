package com.del.app.repository;

import com.del.app.model.Rider;
import com.del.app.model.RiderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByEmail(String email);

    @Query("SELECT r FROM Rider r WHERE r.riderStatus = :status AND ( 6371 * acos( cos( radians(:restaurantLatitude) ) * cos( radians(r.latitude) ) * cos( radians(r.longitude) - radians(:restaurantLongitude) ) + sin( radians(:restaurantLatitude) ) * sin( radians(r.latitude) ) ) ) < :radius")
    List<Rider> findAvailableRidersByLocation(@Param("status") RiderStatus status,
                                              @Param("restaurantLatitude") double restaurantLatitude,
                                              @Param("restaurantLongitude") double restaurantLongitude,
                                              @Param("radius") double radius);

    @Modifying
    @Query("UPDATE Rider r SET r.riderStatus = :newStatus WHERE r.id = :riderId")
    int updateRiderStatus(@Param("riderId") Long riderId, @Param("newStatus") RiderStatus newStatus);
}