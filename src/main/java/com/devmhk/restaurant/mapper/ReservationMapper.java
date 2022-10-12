package com.devmhk.restaurant.mapper;

import com.devmhk.restaurant.admin.dto.ReservationDto;
import com.devmhk.restaurant.admin.model.ReservationParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {

    List<ReservationDto> selectList(ReservationParam reservationParam);
    Long selectListCount(ReservationParam reservationParam);
    List<ReservationDto> selectListMyReservation(ReservationParam ReservationParam);

    Long mySelectListCount(ReservationParam reservationParam);
}
