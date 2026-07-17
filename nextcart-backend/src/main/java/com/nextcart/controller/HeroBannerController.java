package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.HeroBanner;
import com.nextcart.service.HeroBannerService;
import com.nextcart.serviceImpl.HeroBannerServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/hero-banners")
@Produces(MediaType.APPLICATION_JSON)
public class HeroBannerController {
    private final HeroBannerService heroBannerService = new HeroBannerServiceImpl();

    @GET
    public Response getAllHeroBanners() {
        try {
            List<HeroBanner> heroBanners = heroBannerService.getAllHeroBanners();
            return Response.ok(ApiResponse.success(heroBanners)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveHeroBanners() {
        try {
            List<HeroBanner> heroBanners = heroBannerService.getActiveHeroBanners();
            return Response.ok(ApiResponse.success(heroBanners)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getHeroBannerById(@PathParam("id") Integer id) {
        try {
            Optional<HeroBanner> heroBanner = heroBannerService.getHeroBannerById(id);
            if (heroBanner.isPresent()) {
                return Response.ok(ApiResponse.success(heroBanner.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Hero Banner not found"))
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
    public Response createHeroBanner(HeroBanner heroBanner) {
        try {
            HeroBanner createdHeroBanner = heroBannerService.createHeroBanner(heroBanner);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdHeroBanner))
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
    public Response updateHeroBanner(@PathParam("id") Integer id, HeroBanner heroBanner) {
        try {
            heroBanner.setHeroId(id);
            HeroBanner updatedHeroBanner = heroBannerService.updateHeroBanner(heroBanner);
            return Response.ok(ApiResponse.success(updatedHeroBanner)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteHeroBanner(@PathParam("id") Integer id) {
        try {
            heroBannerService.deleteHeroBanner(id);
            return Response.ok(ApiResponse.success("Hero Banner deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
