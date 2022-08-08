package com.devmhk.restaurant.admin;

import com.devmhk.restaurant.customer.domain.Customer;
import com.devmhk.restaurant.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCustomerController {

    private final CustomerService customerService;

    @GetMapping("/admin/customer/list.do")
    public String list(Model model) {
        List<Customer> customers = customerService.list();
        model.addAttribute("customers", customers);
        return "admin/customer/list";
    }
}
