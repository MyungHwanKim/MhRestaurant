package com.devmhk.restaurant.review.dto;

import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.review.domain.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long reviewId;
    private LocalDateTime reservedAt;
    private String comment;
    private int serviceScore;
    private int deliciousScore;
    private int amountScore;
    private boolean updateYn;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getReservedText() {
        return reservedAt != null ? reservedAt.format(formatter) : "";
    }

    public String getCreatedText() {
        return createdAt != null ? createdAt.format(formatter) : "";
    }
    public String getUpdatedText() {
        return updatedAt != null ? updatedAt.format(formatter) : "";
    }

    public static ReviewDto of(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getId())
                .reservedAt(review.getReservedAt())
                .comment(review.getComment())
                .serviceScore(review.getServiceScore())
                .deliciousScore(review.getDeliciousScore())
                .amountScore(review.getAmountScore())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
