package com.nextcart.serviceImpl;

import com.nextcart.dao.NotificationDAO;
import com.nextcart.daoImpl.NotificationDAOImpl;
import com.nextcart.model.Notification;
import com.nextcart.service.NotificationService;

import java.util.List;
import java.util.Optional;

public class NotificationServiceImpl implements NotificationService {
    private final NotificationDAO notificationDAO = new NotificationDAOImpl();

    @Override
    public Optional<Notification> getNotificationById(Integer id) {
        return notificationDAO.findById(id);
    }

    @Override
    public List<Notification> getNotificationsByUserId(Integer userId) {
        return notificationDAO.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotificationsByUserId(Integer userId) {
        return notificationDAO.findByUserIdAndIsRead(userId, false);
    }

    @Override
    public Notification createNotification(Notification notification) {
        return notificationDAO.create(notification);
    }

    @Override
    public Notification markAsRead(Integer id) {
        return notificationDAO.markAsRead(id);
    }

    @Override
    public void deleteNotification(Integer id) {
        notificationDAO.delete(id);
    }
}
