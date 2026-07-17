package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.EmailTemplate;
import com.nextcart.service.EmailTemplateService;
import com.nextcart.serviceImpl.EmailTemplateServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/email-templates")
@Produces(MediaType.APPLICATION_JSON)
public class EmailTemplateController {
    private final EmailTemplateService emailTemplateService = new EmailTemplateServiceImpl();

    @GET
    public Response getAllEmailTemplates() {
        try {
            List<EmailTemplate> templates = emailTemplateService.getAllEmailTemplates();
            return Response.ok(ApiResponse.success(templates)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveEmailTemplates() {
        try {
            List<EmailTemplate> templates = emailTemplateService.getActiveEmailTemplates();
            return Response.ok(ApiResponse.success(templates)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getEmailTemplateById(@PathParam("id") Integer id) {
        try {
            Optional<EmailTemplate> template = emailTemplateService.getEmailTemplateById(id);
            if (template.isPresent()) {
                return Response.ok(ApiResponse.success(template.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Email template not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/slug/{slug}")
    public Response getEmailTemplateBySlug(@PathParam("slug") String slug) {
        try {
            Optional<EmailTemplate> template = emailTemplateService.getEmailTemplateBySlug(slug);
            if (template.isPresent()) {
                return Response.ok(ApiResponse.success(template.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Email template not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmailTemplate(EmailTemplate emailTemplate) {
        try {
            EmailTemplate created = emailTemplateService.createEmailTemplate(emailTemplate);
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
    public Response updateEmailTemplate(@PathParam("id") Integer id, EmailTemplate emailTemplate) {
        try {
            emailTemplate.setEmailTemplateId(id);
            EmailTemplate updated = emailTemplateService.updateEmailTemplate(emailTemplate);
            return Response.ok(ApiResponse.success(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteEmailTemplate(@PathParam("id") Integer id) {
        try {
            emailTemplateService.deleteEmailTemplate(id);
            return Response.ok(ApiResponse.success("Email template deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }
}
