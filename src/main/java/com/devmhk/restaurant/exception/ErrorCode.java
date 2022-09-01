package com.devmhk.restaurant.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CUSTOMER_NOT_FOUND("사용자가 존재하지 않습니다."),
    ALREADY_RESERVED_EXIST("이미 예약 날짜가 존재합니다."),
    NOT_EXIST_RESERVE_TIME("선택하신 예약시간은 이미 마감되었습니다."),
    ;
    private final String message;
}
