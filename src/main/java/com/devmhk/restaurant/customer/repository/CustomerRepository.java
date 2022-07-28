package com.devmhk.restaurant.customer.repository;

import com.devmhk.restaurant.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByEmailAuthKey(String uuid);
}
