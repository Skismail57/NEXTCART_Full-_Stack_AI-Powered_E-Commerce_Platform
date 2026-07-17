package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.SystemSetting;
import com.nextcart.service.SystemSettingService;
import com.nextcart.serviceImpl.SystemSettingServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
public class SystemSettingController {
    private final SystemSettingService systemSettingService = new SystemSettingServiceImpl();

    @GET
    public Response getAllSystemSettings() {
        try {
            List<SystemSetting> settings = systemSettingService.getAllSystemSettings();
            return Response.ok(ApiResponse.success(settings)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/key/{key}")
    public Response getSystemSettingByKey(@PathParam("key") String key) {
        try {
            Optional<SystemSetting> setting = systemSettingService.getSystemSettingByKey(key);
            
            if (setting.isPresent()) {
                return Response.ok(ApiResponse.success(setting.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Setting not found"))
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
    public Response createSystemSetting(SystemSetting systemSetting) {
        try {
            SystemSetting createdSetting = systemSettingService.createSystemSetting(systemSetting);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdSetting))
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
    public Response updateSystemSetting(@PathParam("id") Integer id, SystemSetting systemSetting) {
        try {
            systemSetting.setSettingId(id);
            SystemSetting updatedSetting = systemSettingService.updateSystemSetting(systemSetting);
            return Response.ok(ApiResponse.success(updatedSetting)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/admin/save-or-update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveOrUpdateSystemSetting(SystemSetting systemSetting) {
        try {
            SystemSetting savedSetting = systemSettingService.saveOrUpdateSystemSetting(systemSetting);
            return Response.ok(ApiResponse.success(savedSetting)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteSystemSetting(@PathParam("id") Integer id) {
        try {
            systemSettingService.deleteSystemSetting(id);
            return Response.ok(ApiResponse.success("Setting deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
