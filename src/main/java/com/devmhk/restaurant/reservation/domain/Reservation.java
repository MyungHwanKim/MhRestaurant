package com.devmhk.restaurant.reservation.domain;

import com.devmhk.restaurant.util.status.ReservationStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reserveId;
    private String userId;
    private int headCount; // 예약 인원

    private LocalDateTime reservedAt; // 예약날짜
    private LocalDateTime createdAt; // 예약시점
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // 상태(예약 확정, 예약 취소, 방문 완료)

    private boolean isReview;

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }
}
