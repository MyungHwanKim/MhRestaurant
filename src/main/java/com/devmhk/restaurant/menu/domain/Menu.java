package com.devmhk.restaurant.menu.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    private String division;

    private String name;

    @Lob
    private String content;

    private String price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String fileName;
    private String urlFileName;

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }
}
