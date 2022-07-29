package com.devmhk.restaurant.customer.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer implements StatusCode {
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

    private String status;
}
