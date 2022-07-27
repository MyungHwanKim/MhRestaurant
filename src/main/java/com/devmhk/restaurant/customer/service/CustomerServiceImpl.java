package com.devmhk.restaurant.customer.service;

import com.devmhk.restaurant.customer.model.CustomerInput;
import com.devmhk.restaurant.customer.repository.CustomerRepository;
import com.devmhk.restaurant.customer.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public boolean register(CustomerInput customerInput) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerInput.getUserId());
        if (optionalCustomer.isPresent()) {
            return false;
        }

        String uuid = UUID.randomUUID().toString();

        Customer customer = Customer.builder()
                                    .userId(customerInput.getUserId())
                                    .password(customerInput.getPassword())
                                    .userName(customerInput.getUserName())
                                    .gender(customerInput.getGender())
                                    .phone(customerInput.getPhone())
                                    .email(customerInput.getEmail())
                                    .createdAt(LocalDateTime.now())
                                    .emailAuthYn(false)
                                    .emailAuthKey(uuid)
                                    .build();
        customerRepository.save(customer);

        return true;
    }
}
