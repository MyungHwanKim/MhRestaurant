package com.devmhk.restaurant.menu.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuIntro {

    private String division;
    private String name;
    private String content;
    private Long price;
    private String urlFileName;
}
