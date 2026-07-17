package com.nextcart.analyticsservice.service;

import com.nextcart.analyticsservice.model.DailyAnalytics;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyAnalyticsService {
    List<DailyAnalytics> getAllAnalytics();
    Optional<DailyAnalytics> getAnalyticsByDate(LocalDate date);
    List<DailyAnalytics> getAnalyticsByDateRange(LocalDate startDate, LocalDate endDate);
    DailyAnalytics createAnalytics(DailyAnalytics analytics);
    DailyAnalytics updateAnalytics(Integer id, DailyAnalytics analytics);
    void deleteAnalytics(Integer id);
}
