package com.devmhk.restaurant.customer.controller;

import com.devmhk.restaurant.admin.dto.CustomerDto;
import com.devmhk.restaurant.customer.model.CustomerInput;
import com.devmhk.restaurant.customer.model.ResetPasswordInput;
import com.devmhk.restaurant.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/customer")
@Controller
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping("/sign-in")
    public String signIn() {
        return "customer/sign_in";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "customer/sign_up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(Model model, CustomerInput customerInput) {
        boolean result = customerService.signUp(customerInput);
        model.addAttribute("result", result);

        return "customer/sign_up_completed";
    }

    @GetMapping("/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {
        String uuid = request.getParameter("id");

        boolean result = customerService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "customer/email_auth";
    }

    @GetMapping("/find/password")
    public String findPassword() {
        return "customer/find_password";
    }

    @PostMapping("/find/password")
    public String findPasswordSubmit(Model model, ResetPasswordInput resetPasswordInput) {

        boolean result = false;
        try {
            result = customerService.sendResetPassword(resetPasswordInput);
        } catch (Exception e) {
        }
        model.addAttribute("result", result);

        return "customer/find_password_result";
    }

    @GetMapping("/reset/password")
    public String resetPassword(Model model, HttpServletRequest request) {
        String uuid = request.getParameter("id");
        boolean result = customerService.checkResetPassword(uuid);

        model.addAttribute("result", result);

        return "customer/reset_password";
    }

    @PostMapping("/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput resetPasswordInput) {

        boolean result = false;
        try {
            result = customerService.resetPassword(resetPasswordInput.getId(), resetPasswordInput.getPassword());
        } catch (Exception e) {
        }
        model.addAttribute("result", result);

        return "customer/reset_password_result";
    }

    @GetMapping("/myMain")
    public String myMain() {
        return "customer/myMain";
    }

    @GetMapping("/myMain/myInfo")
    public String myInfo(Model model, Principal principal) {
        String userId = principal.getName();
        CustomerDto myInfo = customerService.myInfo(userId);

        model.addAttribute("myInfo", myInfo);
        return "customer/myInfo";
    }

    @PostMapping("/myMain/myInfo")
    public String myInfoSubmit(CustomerInput customerInput, Principal principal) {
        String userId = principal.getName();
        customerInput.setUserId(userId);

        boolean result = customerService.updateCustomer(customerInput);

        return "redirect:/customer/myMain";
    }

    @GetMapping("/myMain/myInfo/changeName")
    public String changeName() {
        return "customer/changeName";
    }

    @PostMapping("/myMain/myInfo/changeName")
    public String changeNameSubmit(Model model, CustomerInput customerInput, Principal principal) {
        String userId = principal.getName();
        customerInput.setUserId(userId);
        boolean result = customerService.changeName(customerInput);
        if (!result) {
            model.addAttribute("message", "이름 변경에 실패했습니다.");
            return "customer/changeName";
        }
        model.addAttribute("result", result);
        return "customer/changeName_result";
    }

    @GetMapping("/myMain/updatePassword")
    public String updatePassword(Model model, Principal principal) {
        String userId = principal.getName();
        CustomerDto myInfo = customerService.myInfo(userId);

        model.addAttribute("myInfo", myInfo);
        return "customer/updatePassword";
    }

    @PostMapping("/myMain/updatePassword")
    public String updatePasswordSubmit(Model model, CustomerInput customerInput, Principal principal) {
        String userId = principal.getName();
        customerInput.setUserId(userId);
        boolean result = customerService.updatePassword(customerInput);
        if (!result) {
            model.addAttribute("message", "비밀번호 변경에 실패했습니다.");
            return "customer/updatePassword";
        }
        model.addAttribute("result", result);
        return "redirect:/customer/myMain";
    }
}
