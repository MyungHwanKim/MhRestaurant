package com.devmhk.restaurant.admin.dto;

import com.devmhk.restaurant.customer.domain.AccountStatus;
import com.devmhk.restaurant.customer.domain.Customer;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private LocalDateTime updatedAt;
    private boolean adminYn;
    private boolean emailAuthYn;
    private LocalDateTime emailAuthAt;
    private String emailAuthKey;
    private AccountStatus status;
    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitAt;

    public static CustomerDto of(Customer customer) {
        return CustomerDto.builder()
                .userId(customer.getUserId())
                .userName(customer.getUserName())
                .gender(customer.getGender())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .adminYn(customer.isAdminYn())
                .emailAuthYn(customer.isEmailAuthYn())
                .emailAuthAt(customer.getEmailAuthAt())
                .emailAuthKey(customer.getEmailAuthKey())
                .status(customer.getStatus())
                .resetPasswordKey(customer.getResetPasswordKey())
                .resetPasswordLimitAt(customer.getResetPasswordLimitAt())
                .build();
    }

    public String getCreatedText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdAt != null ? createdAt.format(formatter) : "";
    }

    public String getUpdatedText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return updatedAt != null ? updatedAt.format(formatter) : "";
    }


}
