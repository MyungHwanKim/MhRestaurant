package com.devmhk.restaurant.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerDto {
    String userId;
    String password;
    String userName;
    String gender;
    String phone;
    String email;
    LocalDateTime createdAt;
    boolean adminYn;
    boolean emailAuthYn;
    LocalDateTime emailAuthAt;
    String emailAuthKey;
    String status;
    String resetPasswordKey;
    LocalDateTime resetPasswordLimitAt;

    long totalCount;
    long seq;
}
