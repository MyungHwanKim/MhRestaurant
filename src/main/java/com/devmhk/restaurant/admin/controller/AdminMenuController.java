package com.devmhk.restaurant.admin.controller;

import com.devmhk.restaurant.admin.dto.MenuDto;
import com.devmhk.restaurant.admin.model.MenuParam;
import com.devmhk.restaurant.menu.model.MenuInput;
import com.devmhk.restaurant.menu.service.MenuService;
import com.devmhk.restaurant.util.page.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminMenuController extends BaseController {

    private final MenuService menuService;

    @GetMapping("/menu/list.do")
    public String menuList(Model model, MenuParam menuParam){

        menuParam.init();
        List<MenuDto> menuDtoList = menuService.list(menuParam);

        long totalCount = 0;
        if (menuDtoList != null && menuDtoList.size() > 0) {
            totalCount = menuDtoList.get(0).getTotalCount();
        }

        String queryString = menuParam.getQueryString();
        String pageHtml = getPagerHtml(totalCount, menuParam.getPageSize(), menuParam.getPageIndex(), queryString);

        model.addAttribute("menuDtoList", menuDtoList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageHtml", pageHtml);

        return "admin/menu/list";
    }

    @GetMapping(value = {"/menu/register.do", "/menu/edit.do"})
    public String menuRegister(Model model, HttpServletRequest request, MenuInput menuInput) {

        boolean editMode = request.getRequestURI().contains("/edit.do");
        MenuDto menuDto = new MenuDto();

        if (editMode) {
            long id = menuInput.getMenuId();

            MenuDto existMenu = menuService.getById(id);

            if (existMenu == null) {
                model.addAttribute("message", "메뉴 정보가 존재하지 않습니다.");
                return "redirect:/admin/main.do";
            }
            menuDto = existMenu;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("menuDto", menuDto);

        return "admin/menu/register";
    }

    @PostMapping(value = {"/menu/register.do", "/menu/edit.do"})
    public String menuRegisterSubmit(Model model, HttpServletRequest request,
                                     MultipartFile file,
                                     MenuInput menuInput) {

        String saveFileName = "";
        String urlFileName = "";

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            String baseLocalPath = "C:/dev/Reservation_Project/restaurant/src/main/resources/static/files";
            String baseUrlPath = "/files";

            String[] arrFileName = getNewSaveFile(baseLocalPath, baseUrlPath, originalFileName);

            saveFileName = arrFileName[0];
            urlFileName = arrFileName[1];

            try {
                File newFile = new File(saveFileName);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        menuInput.setFileName(saveFileName);
        menuInput.setUrlFileName(urlFileName);

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode) {
            long id = menuInput.getMenuId();

            MenuDto existMenu = menuService.getById(id);

            if (existMenu == null) {
                model.addAttribute("message", "메뉴 정보가 존재하지 않습니다.");
                return "redirect:/admin/main.do";
            }

            menuService.edit(menuInput);
        } else {
            menuService.register(menuInput);
        }

        return "redirect:/admin/menu/list.do";
    }

    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFileName) {

        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())
        };

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFileName != null) {
            int dotPos = originalFileName.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFileName.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFileName = String.format("%s%s", dirs[2], uuid);
        String newUrlFileName = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFileName += "." + fileExtension;
            newUrlFileName += "." + fileExtension;
        }

        return new String[]{newFileName, newUrlFileName};
    }

    @PostMapping("/menu/delete.do")
    public String menuDelete(MenuInput menuInput) {
        menuService.delete(menuInput.getIdList());

        return "redirect:/admin/menu/list.do";
    }
}
