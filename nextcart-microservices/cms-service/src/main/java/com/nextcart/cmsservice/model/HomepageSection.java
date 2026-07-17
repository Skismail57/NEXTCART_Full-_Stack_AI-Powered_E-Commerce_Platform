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
@Table(name = "homepage_sections")
public class HomepageSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Integer id;

    @Column(name = "section_name", nullable = false, length = 200)
    private String sectionName;

    @Column(name = "section_type", nullable = false, length = 100)
    private String sectionType;

    @Column(length = 255)
    private String title;

    @Column(length = 500)
    private String subtitle;

    @Column(length = 100)
    private String layout;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private HomepageSectionStatus status;

    @Column(columnDefinition = "TEXT")
    private String config;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum HomepageSectionStatus {
        ACTIVE,
        INACTIVE
    }
}
