package com.devmhk.restaurant.admin.dto;

import com.devmhk.restaurant.customer.domain.AccountStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
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
    private AccountStatus status;
    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitAt;
}
