package com.devmhk.restaurant.customer.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    private String userId;
    private String password;
    private String userName;
    private String gender;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private boolean adminYn;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthAt;
    private String emailAuthKey;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitAt;
}
