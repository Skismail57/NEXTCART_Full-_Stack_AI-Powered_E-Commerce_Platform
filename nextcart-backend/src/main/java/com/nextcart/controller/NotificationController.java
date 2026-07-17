package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Notification;
import com.nextcart.service.NotificationService;
import com.nextcart.serviceImpl.NotificationServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationController {
    private final NotificationService notificationService = new NotificationServiceImpl();

    @GET
    @Path("/user/{userId}")
    public Response getNotificationsByUserId(@PathParam("userId") Integer userId) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
            return Response.ok(ApiResponse.success(notifications)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/unread")
    public Response getUnreadNotificationsByUserId(@PathParam("userId") Integer userId) {
        try {
            List<Notification> notifications = notificationService.getUnreadNotificationsByUserId(userId);
            return Response.ok(ApiResponse.success(notifications)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getNotificationById(@PathParam("id") Integer id) {
        try {
            Optional<Notification> notification = notificationService.getNotificationById(id);
            
            if (notification.isPresent()) {
                return Response.ok(ApiResponse.success(notification.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Notification not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNotification(Notification notification) {
        try {
            Notification createdNotification = notificationService.createNotification(notification);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdNotification))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/read")
    public Response markAsRead(@PathParam("id") Integer id) {
        try {
            Notification updatedNotification = notificationService.markAsRead(id);
            if (updatedNotification != null) {
                return Response.ok(ApiResponse.success(updatedNotification)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Notification not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNotification(@PathParam("id") Integer id) {
        try {
            notificationService.deleteNotification(id);
            return Response.ok(ApiResponse.success("Notification deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
