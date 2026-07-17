package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Banner;
import com.nextcart.service.BannerService;
import com.nextcart.serviceImpl.BannerServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/banners")
@Produces(MediaType.APPLICATION_JSON)
public class BannerController {
    private final BannerService bannerService = new BannerServiceImpl();

    @GET
    public Response getAllBanners() {
        try {
            List<Banner> banners = bannerService.getAllBanners();
            return Response.ok(ApiResponse.success(banners)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveBanners() {
        try {
            List<Banner> banners = bannerService.getActiveBanners();
            return Response.ok(ApiResponse.success(banners)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getBannerById(@PathParam("id") Integer id) {
        try {
            Optional<Banner> banner = bannerService.getBannerById(id);
            if (banner.isPresent()) {
                return Response.ok(ApiResponse.success(banner.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Banner not found"))
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
    public Response createBanner(Banner banner) {
        try {
            Banner createdBanner = bannerService.createBanner(banner);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdBanner))
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
    public Response updateBanner(@PathParam("id") Integer id, Banner banner) {
        try {
            banner.setBannerId(id);
            Banner updatedBanner = bannerService.updateBanner(banner);
            return Response.ok(ApiResponse.success(updatedBanner)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteBanner(@PathParam("id") Integer id) {
        try {
            bannerService.deleteBanner(id);
            return Response.ok(ApiResponse.success("Banner deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
