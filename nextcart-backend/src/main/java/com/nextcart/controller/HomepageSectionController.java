package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.HomepageSection;
import com.nextcart.service.HomepageSectionService;
import com.nextcart.serviceImpl.HomepageSectionServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/homepage-sections")
@Produces(MediaType.APPLICATION_JSON)
public class HomepageSectionController {
    private final HomepageSectionService sectionService = new HomepageSectionServiceImpl();

    @GET
    public Response getAllSections() {
        try {
            List<HomepageSection> sections = sectionService.getAllSections();
            return Response.ok(ApiResponse.success(sections)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveSections() {
        try {
            List<HomepageSection> sections = sectionService.getActiveSections();
            return Response.ok(ApiResponse.success(sections)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getSectionById(@PathParam("id") Integer id) {
        try {
            Optional<HomepageSection> section = sectionService.getSectionById(id);
            if (section.isPresent()) {
                return Response.ok(ApiResponse.success(section.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Homepage section not found"))
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
    public Response createSection(HomepageSection section) {
        try {
            HomepageSection created = sectionService.createSection(section);
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
    public Response updateSection(@PathParam("id") Integer id, HomepageSection section) {
        try {
            section.setSectionId(id);
            HomepageSection updated = sectionService.updateSection(section);
            return Response.ok(ApiResponse.success(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteSection(@PathParam("id") Integer id) {
        try {
            sectionService.deleteSection(id);
            return Response.ok(ApiResponse.success("Homepage section deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
