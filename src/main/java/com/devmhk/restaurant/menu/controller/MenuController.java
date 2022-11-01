package com.devmhk.restaurant.menu.controller;

import com.devmhk.restaurant.menu.model.MenuIntro;
import com.devmhk.restaurant.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public String menuList(Model model, Principal principal) {

        if (principal == null) {
            model.addAttribute("userId", false);
        } else {
            model.addAttribute("userId", true);
        }

        List<MenuIntro> mainMenuList = menuService.mainMenuList();
        List<MenuIntro> sideMenuList = menuService.sideMenuList();
        List<MenuIntro> drinkMenuList = menuService.drinkMenuList();

        model.addAttribute("mainMenuList", mainMenuList);
        model.addAttribute("sideMenuList", sideMenuList);
        model.addAttribute("drinkMenuList", drinkMenuList);

        return "/menu";
    }
}
