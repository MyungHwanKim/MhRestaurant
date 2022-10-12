package com.devmhk.restaurant.review.controller;

import com.devmhk.restaurant.admin.model.ReviewParam;
import com.devmhk.restaurant.exception.ServiceResult;
import com.devmhk.restaurant.review.dto.ReviewDto;
import com.devmhk.restaurant.review.model.ReviewInput;
import com.devmhk.restaurant.review.service.ReviewService;
import com.devmhk.restaurant.util.page.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.security.Principal;

@RequestMapping("/review")
@Controller
@RequiredArgsConstructor
public class ReviewController extends BaseController {

    private final ReviewService reviewService;

    @GetMapping("/register/{reservedId}")
    public String review(@PathVariable String reservedId, Model model, ReviewInput reviewInput, Principal principal) {
        return "/review/register";
    }

    @PostMapping("/register/{reservedId}")
    public String reviewForm(@PathVariable String reservedId, Model model, ReviewInput reviewInput, Principal principal) {
        String userId = principal.getName();
        reviewInput.setUserId(userId);
        reviewInput.setReserveId(reservedId);
        ServiceResult result = reviewService.register(reviewInput);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "review/register";
        }
        model.addAttribute("result", result);
        return "review/result";
    }

    @GetMapping("/list")
    public String reviewList(Model model, ReviewParam reviewParam, Principal principal) {
        if (principal == null) {
            model.addAttribute("userId", false);
        } else {
            model.addAttribute("userId", true);
        }
        reviewParam.init();
        List<ReviewDto> reviews = reviewService.list(reviewParam);

        long totalCount = 0;
        if (reviews != null && reviews.size() > 0) {
            totalCount = reviewService.totalCount(reviewParam);
        }

        Double avgServiceScore = reviewService.avgServiceScore(reviewParam);
        Double avgDeliciousScore = reviewService.avgDeliciousScore(reviewParam);
        Double avgAmountScore = reviewService.avgAmountScore(reviewParam);
        Double avgTotalScore = reviewService.avgTotalScore(reviewParam);

        String queryString = reviewParam.getQueryString();
        String pageHtml = getPagerHtml(totalCount, reviewParam.getPageSize(), reviewParam.getPageIndex(), queryString);

        model.addAttribute("reviews", reviews);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("avgServiceScore", avgServiceScore);
        model.addAttribute("avgDeliciousScore", avgDeliciousScore);
        model.addAttribute("avgAmountScore", avgAmountScore);
        model.addAttribute("avgTotalScore", avgTotalScore);
        model.addAttribute("pageHtml", pageHtml);

        return "review/list";
    }

    @GetMapping("update/{reviewId}")
    public String updateReview(@PathVariable Long reviewId, Model model) {
        ReviewDto reviewDto = reviewService.getById(reviewId);
        if (reviewDto == null) {
            model.addAttribute("message", "리뷰 정보가 존재하지 않습니다.");
            return "redirect:/customer/myMain/review";
        }
        model.addAttribute("reviewDto", reviewDto);

        return "review/update";
    }

    @PostMapping("update/{reviewId}")
    public String updateReviewForm(@PathVariable Long reviewId, Model model, ReviewInput reviewInput, Principal principal) {
        String userId = principal.getName();
        reviewInput.setUserId(userId);

        ServiceResult result = reviewService.updateReview(reviewId, reviewInput);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "review/update";
        }
        model.addAttribute("result", result);
        return "redirect:/customer/myMain/review";
    }
}
