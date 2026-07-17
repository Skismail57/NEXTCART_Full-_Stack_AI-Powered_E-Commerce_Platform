package com.nextcart.notificationservice.service.impl;

import com.nextcart.notificationservice.model.Notification;
import com.nextcart.notificationservice.repository.NotificationRepository;
import com.nextcart.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserIdAndIsRead(userId, false);
    }

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> markAsRead(Integer id) {
        return notificationRepository.findById(id).map(n -> {
            n.setIsRead(true);
            return notificationRepository.save(n);
        });
    }

    @Override
    public void deleteNotification(Integer id) {
        notificationRepository.deleteById(id);
    }
}
