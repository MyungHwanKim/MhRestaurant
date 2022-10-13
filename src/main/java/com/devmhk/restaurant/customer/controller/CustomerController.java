package com.devmhk.restaurant.customer.controller;

import com.devmhk.restaurant.admin.dto.CustomerDto;
import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.admin.model.ReservationParam;
import com.devmhk.restaurant.admin.model.ReviewParam;
import com.devmhk.restaurant.customer.model.CustomerInput;
import com.devmhk.restaurant.customer.model.ResetPasswordInput;
import com.devmhk.restaurant.customer.service.CustomerService;
import com.devmhk.restaurant.reservation.service.ReservationService;
import com.devmhk.restaurant.review.dto.ReviewDto;
import com.devmhk.restaurant.review.service.ReviewService;
import com.devmhk.restaurant.util.page.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/customer")
@Controller
public class CustomerController extends BaseController {

    private final CustomerService customerService;
    private final ReservationService reservationService;
    private final ReviewService reviewService;

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

    @GetMapping("/myMain/reservation")
    public String myReservation(Model model, ReservationParam reservationParam, Principal principal) {
        reservationParam.init();
        String userId = principal.getName();
        reservationParam.setUserId(userId);
        List<ReservationDto> lists = reservationService.myReservation(reservationParam);
        long totalCount = 0;
        if (lists != null && lists.size() > 0) {
            totalCount = reservationService.myTotalCount(reservationParam);
        }

        String queryString = reservationParam.getQueryString();
        String pageHtml = getPagerHtml(totalCount, reservationParam.getPageSize(), reservationParam.getPageIndex(), queryString);
        model.addAttribute("lists", lists);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageHtml", pageHtml);

        return "customer/reservation";
    }

    @GetMapping("/myMain/review")
    public String myReview(Model model, ReviewParam reviewParam, Principal principal) {
        reviewParam.init();
        String userId = principal.getName();
        List<ReviewDto> lists = reviewService.myReview(reviewParam, userId);
        for (ReviewDto list : lists) {
            if (list.getCreatedAt().plusDays(3).isBefore(LocalDateTime.now())) {
                list.setUpdateYn(false);
            } else {
                list.setUpdateYn(true);
            }
        }
        long totalCount = 0;
        if (lists != null && lists.size() > 0) {
            totalCount = reviewService.myTotalCount(reviewParam);
        }

        String queryString = reviewParam.getQueryString();
        String pageHtml = getPagerHtml(totalCount, reviewParam.getPageSize(), reviewParam.getPageIndex(), queryString);
        model.addAttribute("lists", lists);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageHtml", pageHtml);

        return "customer/review";
    }
}
