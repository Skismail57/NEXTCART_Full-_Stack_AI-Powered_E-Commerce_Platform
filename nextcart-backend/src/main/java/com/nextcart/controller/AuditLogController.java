package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.AuditLog;
import com.nextcart.service.AuditLogService;
import com.nextcart.serviceImpl.AuditLogServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/audit-logs")
@Produces(MediaType.APPLICATION_JSON)
public class AuditLogController {
    private final AuditLogService auditLogService = new AuditLogServiceImpl();

    @GET
    @Path("/admin/all")
    public Response getAllAuditLogs() {
        try {
            List<AuditLog> auditLogs = auditLogService.getAllAuditLogs();
            return Response.ok(ApiResponse.success(auditLogs)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/admin/{id}")
    public Response getAuditLogById(@PathParam("id") Integer id) {
        try {
            Optional<AuditLog> auditLog = auditLogService.getAuditLogById(id);
            if (auditLog.isPresent()) {
                return Response.ok(ApiResponse.success(auditLog.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Audit log not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/admin/user/{userId}")
    public Response getAuditLogsByUserId(@PathParam("userId") Integer userId) {
        try {
            List<AuditLog> auditLogs = auditLogService.getAuditLogsByUserId(userId);
            return Response.ok(ApiResponse.success(auditLogs)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/admin/entity/{entityType}")
    public Response getAuditLogsByEntityType(@PathParam("entityType") String entityType) {
        try {
            List<AuditLog> auditLogs = auditLogService.getAuditLogsByEntityType(entityType);
            return Response.ok(ApiResponse.success(auditLogs)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAuditLog(AuditLog auditLog) {
        try {
            AuditLog created = auditLogService.createAuditLog(auditLog);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(created)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }
}
