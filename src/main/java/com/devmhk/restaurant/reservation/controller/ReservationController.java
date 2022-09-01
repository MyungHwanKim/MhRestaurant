package com.devmhk.restaurant.reservation.controller;

import com.devmhk.restaurant.exception.ServiceResult;
import com.devmhk.restaurant.reservation.model.ReservationInput;
import com.devmhk.restaurant.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/reservation")
@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public String reservation() {
        return "/reservation";
    }

    @PostMapping
    public String reservationForm(Model model, ReservationInput reservationInput, Principal principal) {
        String userId = principal.getName();
        reservationInput.setUserId(userId);

        ServiceResult result = reservationService.reserve(reservationInput);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "/reservation";
        }
        model.addAttribute("result", result);
        return "/reservation_result";
    }
}
