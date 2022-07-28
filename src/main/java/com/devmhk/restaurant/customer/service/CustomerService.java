package com.devmhk.restaurant.customer.service;

import com.devmhk.restaurant.customer.model.CustomerInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {
    /**
     *회원가입
     */
    boolean signUp(CustomerInput customerInput);

    /**
     *이메일 인증
     */
    boolean emailAuth(String uuid);
}
