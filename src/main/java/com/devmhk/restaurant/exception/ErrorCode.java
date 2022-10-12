package com.devmhk.restaurant.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CUSTOMER_NOT_FOUND("사용자가 존재하지 않습니다."),
    ALREADY_EXIST_RESERVED("이미 예약 날짜가 존재합니다."),
    NOT_EXIST_RESERVE_TIME("선택하신 예약시간은 이미 마감되었습니다."),
    ALREADY_EXIST_REVIEW("이미 작성하신 리뷰가 존재합니다."),
    NOT_EXIST_RESERVATION("예약이 존재하지 않습니다."),
    NOT_EXIST_REVIEW("리뷰가 존재하지 않습니다."),
    EXPIRE_UPDATE_PERIOD("수정 기간이 지났습니다."),
    BEFORE_VISIT_RESTAURANT("식당 방문 전입니다.");

    private final String message;
}
