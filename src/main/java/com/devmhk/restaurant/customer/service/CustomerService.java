package com.devmhk.restaurant.customer.service;

import com.devmhk.restaurant.customer.model.CustomerInput;

public interface CustomerService {
    /**
     *회원가입
     */
    boolean register(CustomerInput customerInput);
}
