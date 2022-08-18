package com.devmhk.restaurant.customer.service;

import com.devmhk.restaurant.admin.dto.CustomerDto;
import com.devmhk.restaurant.admin.mapper.CustomerMapper;
import com.devmhk.restaurant.admin.model.CustomerParam;
import com.devmhk.restaurant.component.MailComponent;
import com.devmhk.restaurant.customer.domain.Customer;
import com.devmhk.restaurant.customer.exception.CustomerNotEmailAuthException;
import com.devmhk.restaurant.customer.exception.CustomerSignInException;
import com.devmhk.restaurant.customer.model.CustomerInput;
import com.devmhk.restaurant.customer.model.ResetPasswordInput;
import com.devmhk.restaurant.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devmhk.restaurant.customer.domain.AccountStatus.*;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final MailComponent mailComponent;
    private final CustomerMapper customerMapper;
    @Override
    public boolean signUp(CustomerInput customerInput) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerInput.getUserId());
        if (optionalCustomer.isPresent()) {
            return false;
        }

        String uuid = UUID.randomUUID().toString();
        String encPassword = BCrypt.hashpw(customerInput.getPassword(), BCrypt.gensalt());

        Customer customer = Customer.builder()
                                    .userId(customerInput.getUserId())
                                    .password(encPassword)
                                    .userName(customerInput.getUserName())
                                    .gender(customerInput.getGender())
                                    .phone(customerInput.getPhone())
                                    .email(customerInput.getEmail())
                                    .createdAt(LocalDateTime.now())
                                    .emailAuthYn(false)
                                    .emailAuthKey(uuid)
                                    .status(REQ)
                                    .build();
        customerRepository.save(customer);

        String email = customerInput.getEmail();
        String subject = "MH 식당 : 회원가입 이메일 인증 안내";
        String text = "<p> 안녕하세요. MH 식당입니다. </p>" +
                "<p> 아래 링크를 클릭하셔서 회원가입을 완료해주세요. </p>" +
                "<div><a target='_blank' href='http://localhost:8080/customer/email-auth?id=" +
                uuid + "'> 회원 가입 완료 </a></div>";
        mailComponent.sendMail(email, subject, text);
        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailAuthKey(uuid);

        if (optionalCustomer.isEmpty()) {
            return false;
        }

        Customer customer = optionalCustomer.get();

        if (customer.isEmailAuthYn()) {
            return false;
        }

        customer.setEmailAuthYn(true);
        customer.setEmailAuthAt(LocalDateTime.now());
        customer.setStatus(ING);
        customerRepository.save(customer);

        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput resetPasswordInput) {

        Customer customer = customerRepository.findByUserIdAndUserName(resetPasswordInput.getUserId(), resetPasswordInput.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException(" 고객 정보가 존재하지 않습니다. "));

        String uuid = UUID.randomUUID().toString();

        customer.setResetPasswordKey(uuid);
        customer.setResetPasswordLimitAt(LocalDateTime.now().plusHours(12));
        customerRepository.save(customer);

        String email = customer.getEmail();
        String subject = "MH 식당 : 비밀번호 초기화 안내";
        String text = "<p> 안녕하세요. MH 식당입니다. </p>" +
                "<p> 아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요. </p>" +
                "<div><a target='_blank' href='http://localhost:8080/customer/reset/password?id=" +
                uuid + "'> 비밀번호 초기화 </a></div>";
        mailComponent.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Customer customer = customerRepository.findByResetPasswordKey(uuid)
                .orElseThrow(() -> new UsernameNotFoundException(" 고객 정보가 존재하지 않습니다. "));

        validateCheckResetPassword(customer);

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        customer.setPassword(encPassword);
        customer.setResetPasswordKey(null);
        customer.setResetPasswordLimitAt(null);
        customerRepository.save(customer);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Customer> optionalCustomer = customerRepository.findByResetPasswordKey(uuid);
        if (optionalCustomer.isEmpty()) {
            return false;
        }

        Customer customer = optionalCustomer.get();
        validateCheckResetPassword(customer);

        return true;
    }

    public void validateCheckResetPassword(Customer customer) {
        if (customer.getResetPasswordLimitAt() == null) {
            throw new RuntimeException(" 유효한 날짜가 아닙니다. ");
        }

        if (customer.getResetPasswordLimitAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException(" 유효한 날짜가 아닙니다. ");
        }
    }

    @Override
    public List<CustomerDto> list(CustomerParam customerParam) {
        return customerMapper.selectList(customerParam);
    }

    @Override
    public Long totalCount(CustomerParam customerParam) {
        return customerMapper.selectListCount(customerParam);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(" 고객 정보가 존재하지 않습니다. "));

        if (REQ.equals(customer.getStatus())) {
            throw new CustomerNotEmailAuthException("이메일 인증 후 로그인 해주세요.");
        }

        if (DORMANT.equals(customer.getStatus())) {
            throw new CustomerSignInException("휴먼 고객입니다.");
        }

        if (WITHDRAW.equals(customer.getStatus())) {
            throw new CustomerSignInException("탈퇴된 고객입니다.");
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        grantedAuthorityList.add(new SimpleGrantedAuthority("USER"));

        if (customer.isAdminYn()) {
            grantedAuthorityList.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return new User(customer.getUserId(), customer.getPassword(), grantedAuthorityList);
    }
}
