package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.AnnouncementBar;
import com.nextcart.service.AnnouncementBarService;
import com.nextcart.serviceImpl.AnnouncementBarServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/announcement-bar")
@Produces(MediaType.APPLICATION_JSON)
public class AnnouncementBarController {
    private final AnnouncementBarService announcementService = new AnnouncementBarServiceImpl();

    @GET
    public Response getAllAnnouncements() {
        try {
            List<AnnouncementBar> announcements = announcementService.getAllAnnouncements();
            return Response.ok(ApiResponse.success(announcements)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveAnnouncements() {
        try {
            List<AnnouncementBar> announcements = announcementService.getActiveAnnouncements();
            return Response.ok(ApiResponse.success(announcements)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getAnnouncementById(@PathParam("id") Integer id) {
        try {
            Optional<AnnouncementBar> announcement = announcementService.getAnnouncementById(id);
            if (announcement.isPresent()) {
                return Response.ok(ApiResponse.success(announcement.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Announcement not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAnnouncement(AnnouncementBar announcement) {
        try {
            AnnouncementBar created = announcementService.createAnnouncement(announcement);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(created))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAnnouncement(@PathParam("id") Integer id, AnnouncementBar announcement) {
        try {
            announcement.setAnnouncementId(id);
            AnnouncementBar updated = announcementService.updateAnnouncement(announcement);
            return Response.ok(ApiResponse.success(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteAnnouncement(@PathParam("id") Integer id) {
        try {
            announcementService.deleteAnnouncement(id);
            return Response.ok(ApiResponse.success("Announcement deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
