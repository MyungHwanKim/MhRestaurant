package com.devmhk.restaurant.reservation.service;

import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.admin.model.ReservationParam;
import com.devmhk.restaurant.exception.ServiceResult;
import com.devmhk.restaurant.reservation.model.ReservationInput;

import java.util.List;

public interface ReservationService {

    /**
     * 예약하기
     */
    ServiceResult reserve(ReservationInput reservationInput);

    /**
     * 예약 목록 가져오기(관리자)
     */
    List<ReservationDto> list(ReservationParam reservationParam);

    /**
     * 전체 예약 수
     */
    long totalCount(ReservationParam reservationParam);

    /**
     * 나의 예약 내역
     */
    List<ReservationDto> myReservation(String userId);
}
