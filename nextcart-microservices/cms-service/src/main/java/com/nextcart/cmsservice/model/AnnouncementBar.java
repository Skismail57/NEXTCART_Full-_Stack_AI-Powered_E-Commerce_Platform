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
@Table(name = "announcement_bars")
public class AnnouncementBar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Integer id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(length = 500)
    private String link;

    @Column(name = "background_color", length = 20)
    private String backgroundColor;

    @Column(name = "text_color", length = 20)
    private String textColor;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AnnouncementStatus status;

    @Column(name = "display_order")
    private Integer displayOrder;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum AnnouncementStatus {
        ACTIVE,
        INACTIVE
    }
}
