package com.devmhk.restaurant.reservation.service;

import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.mapper.ReservationMapper;
import com.devmhk.restaurant.admin.model.ReservationParam;
import com.devmhk.restaurant.customer.domain.Customer;
import com.devmhk.restaurant.customer.repository.CustomerRepository;
import com.devmhk.restaurant.exception.ServiceResult;
import com.devmhk.restaurant.reservation.domain.Reservation;
import com.devmhk.restaurant.reservation.model.ReservationInput;
import com.devmhk.restaurant.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.devmhk.restaurant.exception.ErrorCode.*;
import static com.devmhk.restaurant.util.status.ReservationStatus.RESERVATION_COMPLETE;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ServiceResult reserve(ReservationInput reservationInput) {

        LocalDateTime reservedAt = LocalDateTime.of(reservationInput.getReservedDate(), reservationInput.getReservedTime());
        System.out.println(reservedAt);

        Optional<Customer> optionalCustomer = customerRepository.findByUserId(reservationInput.getUserId());
        if (optionalCustomer.isEmpty()) {
            return new ServiceResult(CUSTOMER_NOT_FOUND);
        }

        Optional<Reservation> optionalReservation = reservationRepository.findByUserIdAndReservedAt(reservationInput.getUserId(), reservedAt);
        if (optionalReservation.isPresent()) {
            return new ServiceResult(ALREADY_EXIST_RESERVED);
        }

        long reservedCount = reservationRepository.countByReservedAtAndStatus(
                reservedAt, RESERVATION_COMPLETE);
        if (reservedCount >= 5) {
            return new ServiceResult(NOT_EXIST_RESERVE_TIME);
        }

        StringBuilder reserveId = new StringBuilder();
        String[] items = String.valueOf(reservationInput.getReservedDate()).split("-");
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
                .isReview(false)
                .build();
        reservationRepository.save(reservation);
        return new ServiceResult(true);
    }

    @Override
    public List<ReservationDto> list(ReservationParam reservationParam) {
        return reservationMapper.selectList(reservationParam);
    }

    @Override
    public Long totalCount(ReservationParam reservationParam) {
        return reservationMapper.selectListCount(reservationParam);
    }

    @Override
    public List<ReservationDto> myReservation(ReservationParam reservationParam) {
        return reservationMapper.selectListMyReservation(reservationParam);
    }

    @Override
    public Long myTotalCount(ReservationParam reservationParam) {
        return reservationMapper.mySelectListCount(reservationParam);
    }
}
