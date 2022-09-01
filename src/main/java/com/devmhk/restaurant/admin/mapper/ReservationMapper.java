package com.devmhk.restaurant.admin.mapper;

import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.admin.model.ReservationParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {

    List<ReservationDto> selectList(ReservationParam reservationParam);
    long selectListCount(ReservationParam reservationParam);
    List<ReservationDto> selectListMyReservation(ReservationParam ReservationParam);
}
