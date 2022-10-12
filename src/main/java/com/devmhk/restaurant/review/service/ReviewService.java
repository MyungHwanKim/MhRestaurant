package com.devmhk.restaurant.review.service;

import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.admin.model.ReviewParam;
import com.devmhk.restaurant.exception.ServiceResult;
import com.devmhk.restaurant.review.dto.ReviewDto;
import com.devmhk.restaurant.review.model.ReviewInput;

import java.util.List;

public interface ReviewService {

    /**
     * 예약한 날짜에 대한 리뷰 작성
     */
    ServiceResult register(ReviewInput reviewInput);

    /**
     * 방문자 리뷰 목록
     */
    List<ReviewDto> list(ReviewParam reviewParam);

    /**
     * 전체 리뷰 수
     */
    Long totalCount(ReviewParam reviewParam);

    /**
     * 전체 서비스 평균 점수
     */
    Double avgServiceScore(ReviewParam reviewParam);

    /**
     * 전체 맛 평균 점수
     */
    Double avgDeliciousScore(ReviewParam reviewParam);

    /**
     * 전체 양 평균 점수
     */
    Double avgAmountScore(ReviewParam reviewParam);

    /**
     * 전체 평균 점수
     */
    Double avgTotalScore(ReviewParam reviewParam);

    /**
     * 리뷰 수정
     */
    ServiceResult updateReview(Long reviewId, ReviewInput reviewInput);

    /**
     * 나의 리뷰 내역
     */
    List<ReviewDto> myReview(ReviewParam reviewParam, String userId);

    /**
     * 수정할 리뷰 가져오기
     */
    ReviewDto getById(Long reviewId);

    /**
     * 리뷰 삭제
     */
    void deleteReview(Long reviewId);

    /**
     * 나의 리뷰 수
     */
    Long myTotalCount(ReviewParam reviewParam);
}
