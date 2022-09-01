package com.devmhk.restaurant.reservation.service;

import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.admin.mapper.ReservationMapper;
import com.devmhk.restaurant.admin.model.ReservationParam;
import com.devmhk.restaurant.customer.domain.Customer;
import com.devmhk.restaurant.customer.repository.CustomerRepository;
import com.devmhk.restaurant.exception.ServiceResult;
import com.devmhk.restaurant.reservation.domain.Reservation;
import com.devmhk.restaurant.reservation.model.ReservationInput;
import com.devmhk.restaurant.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.devmhk.restaurant.exception.ErrorCode.*;
import static com.devmhk.restaurant.reservation.domain.ReservationStatus.RESERVATION_COMPLETE;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ServiceResult reserve(ReservationInput reservationInput) {

        String userId = reservationInput.getUserId();
        LocalDate reservedDate = reservationInput.getReservedDate();
        LocalTime reservedTime = reservationInput.getReservedTime();
        LocalDateTime reservedAt = LocalDateTime.of(reservedDate, reservedTime);
        System.out.println(reservedAt);

        Optional<Customer> optionalCustomer = customerRepository.findById(userId);
        if (optionalCustomer.isEmpty()) {
            return new ServiceResult(CUSTOMER_NOT_FOUND);
        }

        Optional<Reservation> optionalReservation = reservationRepository.findByUserIdAndReservedAt(userId, reservedAt);
        if (optionalReservation.isPresent()) {
            return new ServiceResult(ALREADY_RESERVED_EXIST);
        }

        long reservedCount = reservationRepository.countByReservedAtAndStatus(
                reservedAt, RESERVATION_COMPLETE);
        if (reservedCount >= 5) {
            return new ServiceResult(NOT_EXIST_RESERVE_TIME);
        }

        StringBuilder reserveId = new StringBuilder();
        String[] items = String.valueOf(reservedDate).split("-");
        for (String item: items) {
            reserveId.append(item);
        }
        reserveId.append(RandomStringUtils.randomAlphanumeric(6));

        Reservation reservation = Reservation.builder()
                .reserveId(reserveId.toString())
                .userId(reservationInput.getUserId())
                .headCount(reservationInput.getHeadCount())
                .reservedAt(reservedAt)
                .createdAt(LocalDateTime.now())
                .status(RESERVATION_COMPLETE)
                .build();
        reservationRepository.save(reservation);
        return new ServiceResult(true);
    }

    @Override
    public List<ReservationDto> list(ReservationParam reservationParam) {
        return reservationMapper.selectList(reservationParam);
    }

    @Override
    public long totalCount(ReservationParam reservationParam) {
        return reservationMapper.selectListCount(reservationParam);
    }

    @Override
    public List<ReservationDto> myReservation(String userId) {
        ReservationParam reservationParam = new ReservationParam();
        reservationParam.setUserId(userId);
        return reservationMapper.selectListMyReservation(reservationParam);
    }
}
