package com.devmhk.restaurant.review.service;

import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.admin.model.ReviewParam;
import com.devmhk.restaurant.customer.domain.Customer;
import com.devmhk.restaurant.customer.repository.CustomerRepository;
import com.devmhk.restaurant.exception.ServiceResult;
import com.devmhk.restaurant.mapper.ReviewMapper;
import com.devmhk.restaurant.reservation.domain.Reservation;
import com.devmhk.restaurant.reservation.repository.ReservationRepository;
import com.devmhk.restaurant.review.domain.Review;
import com.devmhk.restaurant.review.dto.ReviewDto;
import com.devmhk.restaurant.review.model.ReviewInput;
import com.devmhk.restaurant.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.devmhk.restaurant.exception.ErrorCode.*;
import static com.devmhk.restaurant.util.status.ReservationStatus.VISIT_COMPLETE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public ServiceResult register(ReviewInput reviewInput) {
        Customer customer = customerRepository.findByUserId(reviewInput.getUserId())
                .orElseThrow(() -> new ServiceResult(CUSTOMER_NOT_FOUND));

        Reservation reservation = reservationRepository.findByUserIdAndReserveId(customer.getUserId(), reviewInput.getReserveId())
                .orElseThrow(() -> new ServiceResult(NOT_EXIST_RESERVATION));

        if (reservation.isReview()) {
            throw new ServiceResult(ALREADY_EXIST_REVIEW);
        }

        if (!reservation.getStatus().equals(VISIT_COMPLETE)) {
            throw new ServiceResult(BEFORE_VISIT_RESTAURANT);
        }

        Review review = Review.from(reviewInput, customer, reservation.getReservedAt());
        reviewRepository.save(review);
        reservation.setReview(true);
        reservationRepository.save(reservation);

        return new ServiceResult(true);
    }

    @Override
    public List<ReviewDto> list(ReviewParam reviewParam) {
        return reviewMapper.selectList(reviewParam);
    }

    @Override
    public Long totalCount(ReviewParam reviewParam) {
        return reviewMapper.selectListCount(reviewParam);
    }

    @Override
    public Double avgServiceScore(ReviewParam reviewParam) {
        return reviewMapper.selectServiceAvg(reviewParam);
    }

    @Override
    public Double avgDeliciousScore(ReviewParam reviewParam) {
        return reviewMapper.selectDeliciousAvg(reviewParam);
    }

    @Override
    public Double avgAmountScore(ReviewParam reviewParam) {
        return reviewMapper.selectAmountAvg(reviewParam);
    }

    @Override
    public Double avgTotalScore(ReviewParam reviewParam) {
        return reviewMapper.selectSTotalAvg(reviewParam);
    }

    @Override
    @Transactional
    public ServiceResult updateReview(Long reviewId, ReviewInput reviewInput) {
        boolean customer = customerRepository.existsByUserId(reviewInput.getUserId());
        if (!customer) {
            throw new ServiceResult(CUSTOMER_NOT_FOUND);
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ServiceResult(NOT_EXIST_REVIEW));

        if (review.getCreatedAt().plusDays(3).isBefore(LocalDateTime.now())) {
            throw new ServiceResult(EXPIRE_UPDATE_PERIOD);
        }

        review.setComment(reviewInput.getComment());
        review.setServiceScore(reviewInput.getServiceScore());
        review.setDeliciousScore(reviewInput.getDeliciousScore());
        review.setAmountScore(reviewInput.getAmountScore());

        return new ServiceResult(true);
    }

    @Override
    public List<ReviewDto> myReview(ReviewParam reviewParam, String userId) {
        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new ServiceResult(CUSTOMER_NOT_FOUND));
        reviewParam.setCustomerId(customer.getId());
        return reviewMapper.selectListMyReview(reviewParam);
    }

    @Override
    public ReviewDto getById(Long reviewId) {
        return reviewRepository.findById(reviewId).map(ReviewDto::of).orElse(null);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Long myTotalCount(ReviewParam reviewParam) {
        return reviewMapper.mySelectListCount(reviewParam);
    }
}
