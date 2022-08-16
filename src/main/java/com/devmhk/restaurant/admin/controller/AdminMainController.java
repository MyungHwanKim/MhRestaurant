package com.devmhk.restaurant.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminMainController {

    @GetMapping("/admin/main.do")
    public String main() {
        return "admin/main";
    }
}
