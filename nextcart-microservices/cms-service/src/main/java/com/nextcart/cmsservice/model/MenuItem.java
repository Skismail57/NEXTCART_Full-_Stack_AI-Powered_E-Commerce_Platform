package com.nextcart.cmsservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    private Integer id;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 200)
    private String slug;

    @Column(length = 500)
    private String url;

    @Column(length = 200)
    private String icon;

    @Column(length = 500)
    private String image;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MenuItemStatus status;

    @Column(name = "menu_type", length = 50)
    private String menuType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum MenuItemStatus {
        ACTIVE,
        INACTIVE
    }
}
