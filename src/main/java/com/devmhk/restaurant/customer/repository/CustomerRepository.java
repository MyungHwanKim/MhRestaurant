package com.devmhk.restaurant.customer.repository;

import com.devmhk.restaurant.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    boolean existsByUserId(String userId);
    Optional<Customer> findByUserId(String userId);
    Optional<Customer> findByEmailAuthKey(String uuid);
    Optional<Customer> findByUserIdAndUserName(String userId, String userName);
    Optional<Customer> findByResetPasswordKey(String resetPasswordKey);
}
