package com.devmhk.restaurant.customer.service;

import com.devmhk.restaurant.customer.model.CustomerInput;
import com.devmhk.restaurant.customer.model.ResetPasswordInput;
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

    /**
     * 비밀번호 찾기
     * */
    boolean sendResetPassword(ResetPasswordInput resetPasswordInput);

    /**
     * 비밀번호 재설정
     */
    boolean resetPassword(String uuid, String password);

    /**
     * 비밀번호 재설정 후 링크 막기
     */
    boolean checkResetPassword(String uuid);
}
