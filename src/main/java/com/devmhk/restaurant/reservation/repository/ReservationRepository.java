package com.devmhk.restaurant.reservation.repository;

import com.devmhk.restaurant.reservation.domain.Reservation;
import com.devmhk.restaurant.util.status.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUserIdAndReservedAtBetween(String userId, LocalDateTime start, LocalDateTime end);
    boolean existsByUserIdAndStatus(String userId, ReservationStatus status);
    Long countByReservedAtAndStatus(LocalDateTime reservedAt, ReservationStatus status);
    Optional<Reservation> findByUserIdAndReserveId(String userId, String reserveId);
}
