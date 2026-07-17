package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.HomepageBanner;
import com.nextcart.service.HomepageBannerService;
import com.nextcart.serviceImpl.HomepageBannerServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/homepage-banners")
@Produces(MediaType.APPLICATION_JSON)
public class HomepageBannerController {
    private final HomepageBannerService bannerService = new HomepageBannerServiceImpl();

    @GET
    public Response getAllBanners() {
        try {
            List<HomepageBanner> banners = bannerService.getAllBanners();
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
            List<HomepageBanner> banners = bannerService.getActiveBanners();
            return Response.ok(ApiResponse.success(banners)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/position/{position}")
    public Response getBannersByPosition(@PathParam("position") String position) {
        try {
            List<HomepageBanner> banners = bannerService.getBannersByPosition(position);
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
            Optional<HomepageBanner> banner = bannerService.getBannerById(id);
            if (banner.isPresent()) {
                return Response.ok(ApiResponse.success(banner.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Homepage banner not found"))
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
    public Response createBanner(HomepageBanner banner) {
        try {
            HomepageBanner created = bannerService.createBanner(banner);
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
    public Response updateBanner(@PathParam("id") Integer id, HomepageBanner banner) {
        try {
            banner.setBannerId(id);
            HomepageBanner updated = bannerService.updateBanner(banner);
            return Response.ok(ApiResponse.success(updated)).build();
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
            return Response.ok(ApiResponse.success("Homepage banner deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
