package com.devmhk.restaurant.reservation.repository;

import com.devmhk.restaurant.reservation.domain.Reservation;
import com.devmhk.restaurant.reservation.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByUserIdAndReservedAt(String userId, LocalDateTime reservedAt);
    Long countByReservedAtAndStatus(LocalDateTime reservedAt, ReservationStatus status);
}
