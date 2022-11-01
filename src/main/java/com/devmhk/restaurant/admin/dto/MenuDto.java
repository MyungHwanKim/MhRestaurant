package com.devmhk.restaurant.admin.dto;

import com.devmhk.restaurant.menu.domain.Menu;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto {

    private Long menuId;

    private String division;

    private String name;

    private String content;

    private String price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String fileName;
    private String urlFileName;

    private Long totalCount;
    private Long seq;

    public static MenuDto of(Menu menu) {
        return MenuDto.builder()
                .menuId(menu.getId())
                .division(menu.getDivision())
                .name(menu.getName())
                .content(menu.getContent())
                .price(menu.getPrice())
                .createdAt(menu.getCreatedAt())
                .updatedAt(menu.getUpdatedAt())
                .fileName(menu.getFileName())
                .urlFileName(menu.getUrlFileName())
                .build();
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getCreatedText() {
        return createdAt != null ? createdAt.format(formatter) : "";
    }

    public String getUpdatedText() {
        return updatedAt != null ? updatedAt.format(formatter) : "";
    }
}
