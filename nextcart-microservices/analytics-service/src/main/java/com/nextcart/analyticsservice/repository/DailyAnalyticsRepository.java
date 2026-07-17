package com.nextcart.analyticsservice.repository;

import com.nextcart.analyticsservice.model.DailyAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyAnalyticsRepository extends JpaRepository<DailyAnalytics, Integer> {
    Optional<DailyAnalytics> findByDate(LocalDate date);
    List<DailyAnalytics> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
