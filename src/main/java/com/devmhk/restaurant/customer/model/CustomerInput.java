package com.devmhk.restaurant.customer.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerInput {
    private String userId;
    private String password;
    private String userName;
    private String gender;
    private String phone;
    private String email;

    private String newPassword;
}
