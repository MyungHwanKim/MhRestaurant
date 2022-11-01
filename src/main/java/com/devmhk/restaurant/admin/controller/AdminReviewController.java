package com.devmhk.restaurant.admin.controller;

import com.devmhk.restaurant.admin.model.ReviewParam;
import com.devmhk.restaurant.review.dto.ReviewDto;
import com.devmhk.restaurant.review.service.ReviewService;
import com.devmhk.restaurant.util.page.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminReviewController extends BaseController {

    private final ReviewService reviewService;

    @GetMapping("/review/list.do")
    public String list(Model model, ReviewParam reviewParam) {
        reviewParam.init();
        List<ReviewDto> reviews = reviewService.list(reviewParam);

        long totalCount = 0;
        if (reviews != null && reviews.size() > 0) {
            totalCount = reviewService.totalCount(reviewParam);
        }

        String queryString = reviewParam.getQueryString();
        String pageHtml = getPagerHtml(totalCount, reviewParam.getPageSize(), reviewParam.getPageIndex(), queryString);

        model.addAttribute("reviews", reviews);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageHtml", pageHtml);

        return "admin/review/list";
    }

    @PostMapping("/review/delete.do")
    public String deleteReview(Long reviewId) {
        reviewService.deleteReview(reviewId);
        return "redirect:/admin/review/list.do";
    }
}
