package com.nextcart.dao;

import com.nextcart.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationDAO {
    Optional<Notification> findById(Integer id);
    List<Notification> findByUserId(Integer userId);
    List<Notification> findByUserIdAndIsRead(Integer userId, Boolean isRead);
    Notification create(Notification notification);
    Notification markAsRead(Integer id);
    void delete(Integer id);
}
