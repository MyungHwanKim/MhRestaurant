package com.devmhk.restaurant.mapper;

import com.devmhk.restaurant.admin.dto.MenuDto;
import com.devmhk.restaurant.admin.model.MenuParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<MenuDto> selectList(MenuParam menuParam);

    Long selectListCount(MenuParam menuParam);
}
