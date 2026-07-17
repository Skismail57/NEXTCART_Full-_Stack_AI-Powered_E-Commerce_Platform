package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Permission;
import com.nextcart.service.PermissionService;
import com.nextcart.serviceImpl.PermissionServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/permissions")
@Produces(MediaType.APPLICATION_JSON)
public class PermissionController {
    private final PermissionService permissionService = new PermissionServiceImpl();

    @GET
    public Response getAllPermissions() {
        try {
            List<Permission> permissions = permissionService.getAllPermissions();
            return Response.ok(ApiResponse.success(permissions)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getPermissionById(@PathParam("id") Integer id) {
        try {
            Optional<Permission> permission = permissionService.getPermissionById(id);
            if (permission.isPresent()) {
                return Response.ok(ApiResponse.success(permission.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Permission not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/slug/{slug}")
    public Response getPermissionBySlug(@PathParam("slug") String slug) {
        try {
            Optional<Permission> permission = permissionService.getPermissionBySlug(slug);
            if (permission.isPresent()) {
                return Response.ok(ApiResponse.success(permission.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Permission not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPermission(Permission permission) {
        try {
            Permission created = permissionService.createPermission(permission);
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
    public Response updatePermission(@PathParam("id") Integer id, Permission permission) {
        try {
            permission.setPermissionId(id);
            Permission updated = permissionService.updatePermission(permission);
            return Response.ok(ApiResponse.success(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deletePermission(@PathParam("id") Integer id) {
        try {
            permissionService.deletePermission(id);
            return Response.ok(ApiResponse.success("Permission deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }
}
