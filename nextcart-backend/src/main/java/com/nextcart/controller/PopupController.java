package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Popup;
import com.nextcart.service.PopupService;
import com.nextcart.serviceImpl.PopupServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/popups")
@Produces(MediaType.APPLICATION_JSON)
public class PopupController {
    private final PopupService popupService = new PopupServiceImpl();

    @GET
    public Response getAllPopups() {
        try {
            List<Popup> popups = popupService.getAllPopups();
            return Response.ok(ApiResponse.success(popups)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/active")
    public Response getActivePopups() {
        try {
            List<Popup> popups = popupService.getActivePopups();
            return Response.ok(ApiResponse.success(popups)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getPopupById(@PathParam("id") Integer id) {
        try {
            Optional<Popup> popup = popupService.getPopupById(id);
            if (popup.isPresent()) {
                return Response.ok(ApiResponse.success(popup.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Popup not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPopup(Popup popup) {
        try {
            Popup created = popupService.createPopup(popup);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(created)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/admin/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePopup(@PathParam("id") Integer id, Popup popup) {
        try {
            popup.setPopupId(id);
            Popup updated = popupService.updatePopup(popup);
            return Response.ok(ApiResponse.success(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deletePopup(@PathParam("id") Integer id) {
        try {
            popupService.deletePopup(id);
            return Response.ok(ApiResponse.success("Popup deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }
}
