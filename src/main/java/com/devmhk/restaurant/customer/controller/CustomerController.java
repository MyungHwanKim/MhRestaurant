package com.devmhk.restaurant.customer.controller;

import com.devmhk.restaurant.customer.model.CustomerInput;
import com.devmhk.restaurant.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping("/customer/sign-in")
    public String signIn() {
        return "customer/sign_in";
    }

    @GetMapping("/customer/sign-up")
    public String signUp() {
        return "customer/sign_up";
    }

    @PostMapping("/customer/sign-up")
    public String signUpSubmit(Model model, CustomerInput customerInput) {
        boolean result = customerService.signUp(customerInput);
        model.addAttribute("result", result);

        return "customer/sign_up_completed";
    }

    @GetMapping("/customer/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {
        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = customerService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "customer/email_auth";
    }
}
