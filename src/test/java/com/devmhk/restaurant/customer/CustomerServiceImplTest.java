package com.devmhk.restaurant.customer;

import com.devmhk.restaurant.customer.domain.Customer;
import com.devmhk.restaurant.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CustomerServiceImplTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void registerTest() {
        // given
        Customer choi = Customer.builder()
                .userId("cu123")
                .password("1234")
                .userName("최웅")
                .gender("남성")
                .phone("010-1111-2222")
                .email("choi@naver.com")
                .build();
        // when
        Customer customer = customerRepository.save(choi);
        // then
        Optional<Customer> result = customerRepository.findById(customer.getUserId());
        assertEquals("cu123", result.get().getUserId());
    }
}