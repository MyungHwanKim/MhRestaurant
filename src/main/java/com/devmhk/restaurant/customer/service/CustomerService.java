package com.devmhk.restaurant.customer.service;

import com.devmhk.restaurant.admin.dto.CustomerDto;
import com.devmhk.restaurant.admin.model.CustomerParam;
import com.devmhk.restaurant.customer.model.CustomerInput;
import com.devmhk.restaurant.customer.model.DeleteAccountInput;
import com.devmhk.restaurant.customer.model.ResetPasswordInput;
import com.devmhk.restaurant.exception.ServiceResult;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

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

    /**
     * 고객 목록 가져오기(관리자)
     */
    List<CustomerDto> list(CustomerParam customerParam);

    /**
     * 전체 고객 수
     */
    Long totalCount(CustomerParam customerParam);

    /**
     * 회원 상세 정보
     */
    CustomerDto myInfo(String userId);

    /**
     * 비밀번호 변경
     */
    boolean updatePassword(CustomerInput customerInput);

    /**
     * 회원 정보 수정
     */
    boolean updateCustomer(CustomerInput customerInput);

    /**
     * 이름 변경
     */
    boolean changeName(CustomerInput customerInput);

    /**
     * 회원 탈퇴
     */
    ServiceResult deleteAccount(DeleteAccountInput input);
}
