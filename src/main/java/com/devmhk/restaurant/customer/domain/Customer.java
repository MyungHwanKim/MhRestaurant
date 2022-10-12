package com.devmhk.restaurant.customer.domain;


import com.devmhk.restaurant.review.domain.Review;
import com.devmhk.restaurant.util.status.AccountStatus;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String userName;

    @NotNull
    private String gender;

    @NotNull
    private String phone;

    @NotNull
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean adminYn;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthAt;
    private String emailAuthKey;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitAt;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "customer")
    private List<Review> reviewList;

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }
}
