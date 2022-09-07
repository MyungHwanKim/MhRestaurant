package com.devmhk.restaurant.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResult extends RuntimeException {

    private boolean result;
    private ErrorCode errorCode;
    private String message;

    public ServiceResult(ErrorCode errorCode) {
        this.result = false;
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public ServiceResult(boolean result) {
        this.result = result;
    }
}
