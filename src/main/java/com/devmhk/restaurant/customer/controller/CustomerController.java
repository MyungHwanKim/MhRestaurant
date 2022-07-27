package com.devmhk.restaurant.customer.controller;

import com.devmhk.restaurant.customer.model.CustomerInput;
import com.devmhk.restaurant.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping("/customer/login")
    public String login() {
        return "customer/login";
    }

    @GetMapping("/customer/register")
    public String register() {
        return "customer/register";
    }

    @PostMapping("/customer/register")
    public String registerSubmit(Model model, CustomerInput customerInput) {
        boolean result = customerService.register(customerInput);
        model.addAttribute("result", result);

        return "customer/register_completed";
    }
}
