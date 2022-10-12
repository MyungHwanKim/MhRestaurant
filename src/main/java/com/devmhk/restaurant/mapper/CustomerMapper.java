package com.devmhk.restaurant.mapper;

import com.devmhk.restaurant.admin.dto.CustomerDto;
import com.devmhk.restaurant.admin.model.CustomerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {

    List<CustomerDto> selectList(CustomerParam customerParam);
    long selectListCount(CustomerParam customerParam);
}
