package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.RolePermission;
import com.nextcart.service.RolePermissionService;
import com.nextcart.serviceImpl.RolePermissionServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/role-permissions")
@Produces(MediaType.APPLICATION_JSON)
public class RolePermissionController {
    private final RolePermissionService rolePermissionService = new RolePermissionServiceImpl();

    @GET
    public Response getAllRolePermissions() {
        try {
            List<RolePermission> rolePermissions = rolePermissionService.getAllRolePermissions();
            return Response.ok(ApiResponse.success(rolePermissions)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/role/{roleId}")
    public Response getRolePermissionsByRoleId(@PathParam("roleId") Integer roleId) {
        try {
            List<RolePermission> rolePermissions = rolePermissionService.getRolePermissionsByRoleId(roleId);
            return Response.ok(ApiResponse.success(rolePermissions)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/permission/{permissionId}")
    public Response getRolePermissionsByPermissionId(@PathParam("permissionId") Integer permissionId) {
        try {
            List<RolePermission> rolePermissions = rolePermissionService.getRolePermissionsByPermissionId(permissionId);
            return Response.ok(ApiResponse.success(rolePermissions)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getRolePermissionById(@PathParam("id") Integer id) {
        try {
            Optional<RolePermission> rolePermission = rolePermissionService.getRolePermissionById(id);
            if (rolePermission.isPresent()) {
                return Response.ok(ApiResponse.success(rolePermission.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Role permission not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRolePermission(RolePermission rolePermission) {
        try {
            RolePermission created = rolePermissionService.createRolePermission(rolePermission);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(created)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/admin/role/{roleId}/permission/{permissionId}")
    public Response deleteRolePermissionByRoleIdAndPermissionId(@PathParam("roleId") Integer roleId,
                                                                 @PathParam("permissionId") Integer permissionId) {
        try {
            rolePermissionService.deleteRolePermissionByRoleIdAndPermissionId(roleId, permissionId);
            return Response.ok(ApiResponse.success("Role permission deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteRolePermission(@PathParam("id") Integer id) {
        try {
            rolePermissionService.deleteRolePermission(id);
            return Response.ok(ApiResponse.success("Role permission deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }
}
