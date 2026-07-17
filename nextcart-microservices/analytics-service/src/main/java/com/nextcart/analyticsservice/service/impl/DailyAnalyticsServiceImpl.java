package com.nextcart.analyticsservice.service.impl;

import com.nextcart.analyticsservice.model.DailyAnalytics;
import com.nextcart.analyticsservice.repository.DailyAnalyticsRepository;
import com.nextcart.analyticsservice.service.DailyAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyAnalyticsServiceImpl implements DailyAnalyticsService {
    private final DailyAnalyticsRepository analyticsRepository;

    @Override
    public List<DailyAnalytics> getAllAnalytics() {
        return analyticsRepository.findAll();
    }

    @Override
    public Optional<DailyAnalytics> getAnalyticsByDate(LocalDate date) {
        return analyticsRepository.findByDate(date);
    }

    @Override
    public List<DailyAnalytics> getAnalyticsByDateRange(LocalDate startDate, LocalDate endDate) {
        return analyticsRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public DailyAnalytics createAnalytics(DailyAnalytics analytics) {
        return analyticsRepository.save(analytics);
    }

    @Override
    public DailyAnalytics updateAnalytics(Integer id, DailyAnalytics analytics) {
        return analyticsRepository.findById(id).map(existing -> {
            existing.setDate(analytics.getDate());
            existing.setTotalRevenue(analytics.getTotalRevenue());
            existing.setTotalOrders(analytics.getTotalOrders());
            existing.setTotalItemsSold(analytics.getTotalItemsSold());
            existing.setAverageOrderValue(analytics.getAverageOrderValue());
            return analyticsRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Analytics not found"));
    }

    @Override
    public void deleteAnalytics(Integer id) {
        analyticsRepository.deleteById(id);
    }
}
