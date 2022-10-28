package com.devmhk.restaurant.menu.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MenuInput {

    private Long menuId;

    @NotBlank(message = "메뉴명은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "메뉴 설명은 필수 항목입니다.")
    private String content;

    @NotBlank(message = "메뉴 가격은 필수 항목입니다.")
    private Long price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String fileName;
    private String urlFileName;

    private String idList;
}
