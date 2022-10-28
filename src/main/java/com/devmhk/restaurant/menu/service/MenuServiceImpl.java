package com.devmhk.restaurant.menu.service;

import com.devmhk.restaurant.admin.dto.MenuDto;
import com.devmhk.restaurant.admin.model.MenuParam;
import com.devmhk.restaurant.exception.ServiceResult;
import com.devmhk.restaurant.mapper.MenuMapper;
import com.devmhk.restaurant.menu.domain.Menu;
import com.devmhk.restaurant.menu.model.MenuInput;
import com.devmhk.restaurant.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.devmhk.restaurant.exception.ErrorCode.NOT_MENU_INFO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public List<MenuDto> list(MenuParam menuParam) {
        Long totalCount = menuMapper.selectListCount(menuParam);
        List<MenuDto> menuDtoList = menuMapper.selectList(menuParam);
        if (!CollectionUtils.isEmpty(menuDtoList)) {
            int i = 0;
            for (MenuDto menu : menuDtoList) {
                menu.setTotalCount(totalCount);
                menu.setSeq(totalCount - menuParam.getPageStart() - i);
                i++;
            }
        }
        return menuDtoList;
    }

    @Override
    public MenuDto getById(long id) {

        return menuRepository.findById(id).map(MenuDto::of).orElse(null);
    }

    @Override
    @Transactional
    public void edit(MenuInput menuInput) {

        Menu menu = menuRepository.findById(menuInput.getMenuId())
                .orElseThrow(() -> new ServiceResult(NOT_MENU_INFO));

        menu.setName(menuInput.getName());
        menu.setContent(menuInput.getContent());
        menu.setPrice(menuInput.getPrice());
        menu.setFileName(menuInput.getFileName());
        menu.setUrlFileName(menuInput.getUrlFileName());
    }

    @Override
    @Transactional
    public void register(MenuInput menuInput) {
        Menu menu = Menu.builder()
                .name(menuInput.getName())
                .content(menuInput.getContent())
                .price(menuInput.getPrice())
                .createdAt(LocalDateTime.now())
                .fileName(menuInput.getFileName())
                .urlFileName(menuInput.getUrlFileName())
                .build();

        menuRepository.save(menu);
    }

    @Override
    @Transactional
    public void delete(String idList) {

        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");

            for (String id : ids) {
                long temp = 0;

                try {
                    temp = Long.parseLong(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (temp > 0) {
                    menuRepository.deleteById(temp);
                }
            }
        }
    }
}