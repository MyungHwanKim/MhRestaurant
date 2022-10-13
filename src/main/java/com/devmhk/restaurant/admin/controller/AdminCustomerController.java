package com.devmhk.restaurant.admin.controller;

import com.devmhk.restaurant.admin.dto.CustomerDto;
import com.devmhk.restaurant.admin.model.CustomerParam;
import com.devmhk.restaurant.customer.service.CustomerService;
import com.devmhk.restaurant.util.page.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCustomerController extends BaseController {

    private final CustomerService customerService;

    @GetMapping("/admin/customer/list.do")
    public String list(Model model, CustomerParam customerParam) {
        customerParam.init();
        List<CustomerDto> customers = customerService.list(customerParam);
        long totalCount = 0;
        if (customers != null && customers.size() > 0) {
            totalCount = customerService.totalCount(customerParam);
        }

        String queryString = customerParam.getQueryString();
        String pageHtml = getPagerHtml(totalCount, customerParam.getPageSize(), customerParam.getPageIndex(), queryString);

        model.addAttribute("customers", customers);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageHtml", pageHtml);

        return "admin/customer/list";
    }
}
