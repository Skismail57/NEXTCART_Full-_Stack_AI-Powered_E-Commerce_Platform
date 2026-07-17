package com.nextcart.notificationservice.service;

import com.nextcart.notificationservice.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<Notification> getNotificationsByUserId(Integer userId);
    List<Notification> getUnreadNotificationsByUserId(Integer userId);
    Notification createNotification(Notification notification);
    Optional<Notification> markAsRead(Integer id);
    void deleteNotification(Integer id);
}
