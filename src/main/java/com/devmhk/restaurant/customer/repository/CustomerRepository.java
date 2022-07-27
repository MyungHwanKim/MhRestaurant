package com.devmhk.restaurant.customer.repository;

import com.devmhk.restaurant.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
