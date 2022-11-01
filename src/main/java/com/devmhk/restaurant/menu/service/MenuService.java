package com.devmhk.restaurant.menu.service;

import com.devmhk.restaurant.admin.dto.MenuDto;
import com.devmhk.restaurant.admin.model.MenuParam;
import com.devmhk.restaurant.menu.model.MenuInput;
import com.devmhk.restaurant.menu.model.MenuIntro;

import java.util.List;

public interface MenuService {
    /**
     * 메뉴 목록
     */
    List<MenuDto> list(MenuParam menuParam);

    /**
     * 메뉴 상세 정보
     */
    MenuDto getById(long id);

    /**
     * 메뉴 정보 수정
     */
    void edit(MenuInput menuInput);

    /**
     * 메뉴 등록
     */
    void register(MenuInput menuInput);

    /**
     * 메뉴 삭제
     */
    void delete(String idList);

    /**
     * 메인 메뉴 목록
     */
    List<MenuIntro> mainMenuList();

    /**
     * 사이드 메뉴 목록
     */
    List<MenuIntro> sideMenuList();

    /**
     * 음료 메뉴 목록
     */
    List<MenuIntro> drinkMenuList();
}
