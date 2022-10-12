package com.devmhk.restaurant.review.domain;

import com.devmhk.restaurant.customer.domain.Customer;
import com.devmhk.restaurant.review.model.ReviewInput;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private LocalDateTime reservedAt;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private int serviceScore;
    private int deliciousScore;
    private int amountScore;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Review from(ReviewInput reviewInput, Customer customer, LocalDateTime reservedAt) {
        return Review.builder()
                .comment(reviewInput.getComment())
                .reservedAt(reservedAt)
                .customer(customer)
                .serviceScore(reviewInput.getServiceScore())
                .deliciousScore(reviewInput.getDeliciousScore())
                .amountScore(reviewInput.getAmountScore())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }
}
