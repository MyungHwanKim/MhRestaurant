package com.devmhk.restaurant.admin.controller;

import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.admin.model.ReservationParam;
import com.devmhk.restaurant.reservation.service.ReservationService;
import com.devmhk.restaurant.util.page.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminReservationController extends BaseController {
    private final ReservationService reservationService;

    @GetMapping("/admin/reservation/list.do")
    public String list(Model model, ReservationParam reservationParam) {
        reservationParam.init();
        List<ReservationDto> reservations = reservationService.list(reservationParam);
        long totalCount = 0;
        if (reservations != null && reservations.size() > 0) {
            totalCount = reservationService.totalCount(reservationParam);
        }

        String queryString = reservationParam.getQueryString();
        String pageHtml = getPagerHtml(totalCount, reservationParam.getPageSize(), reservationParam.getPageIndex(), queryString);

        model.addAttribute("reservations", reservations);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageHtml", pageHtml);

        return "admin/reservation/list";
    }
}
