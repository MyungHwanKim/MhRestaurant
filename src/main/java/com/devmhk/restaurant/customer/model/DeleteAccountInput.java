package com.devmhk.restaurant.customer.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DeleteAccountInput {

    private String userId;

    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    private String password;
}
