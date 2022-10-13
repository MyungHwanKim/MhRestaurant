package com.devmhk.restaurant.admin.dto;

import com.devmhk.restaurant.util.status.ReservationStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private String reserveId;
    private String userId;
    private int headCount;

    private LocalDateTime reservedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private boolean isReview;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter reservedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public String getCreatedText() {
        return createdAt != null ? createdAt.format(formatter) : "";
    }

    public String getUpdatedText() {
        return updatedAt != null ? updatedAt.format(formatter) : "";
    }

    public String getReservedAtText() {
        return reservedAt != null ? reservedAt.format(reservedFormatter) : "";
    }
}
