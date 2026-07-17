package com.nextcart.service;

import com.nextcart.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Optional<Notification> getNotificationById(Integer id);
    List<Notification> getNotificationsByUserId(Integer userId);
    List<Notification> getUnreadNotificationsByUserId(Integer userId);
    Notification createNotification(Notification notification);
    Notification markAsRead(Integer id);
    void deleteNotification(Integer id);
}
