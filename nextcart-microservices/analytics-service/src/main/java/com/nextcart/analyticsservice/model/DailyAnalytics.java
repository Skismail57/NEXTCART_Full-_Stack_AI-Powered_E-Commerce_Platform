package com.nextcart.analyticsservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "daily_analytics")
public class DailyAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analytics_id")
    private Integer id;

    @Column(name = "date", nullable = false, unique = true)
    private LocalDate date;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalRevenue;

    @Column(nullable = false)
    private Integer totalOrders;

    @Column(nullable = false)
    private Integer totalItemsSold;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal averageOrderValue;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
