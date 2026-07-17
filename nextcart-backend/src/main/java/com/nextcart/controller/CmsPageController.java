package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.CmsPage;
import com.nextcart.service.CmsPageService;
import com.nextcart.serviceImpl.CmsPageServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/cms")
@Produces(MediaType.APPLICATION_JSON)
public class CmsPageController {
    private final CmsPageService cmsPageService = new CmsPageServiceImpl();

    @GET
    public Response getAllCmsPages() {
        try {
            List<CmsPage> cmsPages = cmsPageService.getAllCmsPages();
            return Response.ok(ApiResponse.success(cmsPages)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getCmsPageById(@PathParam("id") Integer id) {
        try {
            Optional<CmsPage> cmsPage = cmsPageService.getCmsPageById(id);
            
            if (cmsPage.isPresent()) {
                return Response.ok(ApiResponse.success(cmsPage.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("CMS page not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/slug/{slug}")
    public Response getCmsPageBySlug(@PathParam("slug") String slug) {
        try {
            Optional<CmsPage> cmsPage = cmsPageService.getCmsPageBySlug(slug);
            
            if (cmsPage.isPresent()) {
                return Response.ok(ApiResponse.success(cmsPage.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("CMS page not found"))
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
    public Response createCmsPage(CmsPage cmsPage) {
        try {
            CmsPage createdCmsPage = cmsPageService.createCmsPage(cmsPage);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdCmsPage))
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
    public Response updateCmsPage(@PathParam("id") Integer id, CmsPage cmsPage) {
        try {
            cmsPage.setPageId(id);
            CmsPage updatedCmsPage = cmsPageService.updateCmsPage(cmsPage);
            return Response.ok(ApiResponse.success(updatedCmsPage)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteCmsPage(@PathParam("id") Integer id) {
        try {
            cmsPageService.deleteCmsPage(id);
            return Response.ok(ApiResponse.success("CMS page deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
