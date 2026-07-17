package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Brand;
import com.nextcart.service.BrandService;
import com.nextcart.serviceImpl.BrandServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/brands")
@Produces(MediaType.APPLICATION_JSON)
public class BrandController {
    private final BrandService brandService = new BrandServiceImpl();

    @GET
    public Response getAllBrands() {
        try {
            List<Brand> brands = brandService.getAllBrands();
            return Response.ok(ApiResponse.success(brands)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getBrandById(@PathParam("id") Integer id) {
        try {
            Optional<Brand> brand = brandService.getBrandById(id);
            
            if (brand.isPresent()) {
                return Response.ok(ApiResponse.success(brand.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Brand not found"))
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
    public Response getBrandBySlug(@PathParam("slug") String slug) {
        try {
            Optional<Brand> brand = brandService.getBrandBySlug(slug);
            
            if (brand.isPresent()) {
                return Response.ok(ApiResponse.success(brand.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Brand not found"))
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
    public Response createBrand(Brand brand) {
        try {
            Brand createdBrand = brandService.createBrand(brand);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdBrand))
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
    public Response updateBrand(@PathParam("id") Integer id, Brand brand) {
        try {
            brand.setBrandId(id);
            Brand updatedBrand = brandService.updateBrand(brand);
            return Response.ok(ApiResponse.success(updatedBrand)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteBrand(@PathParam("id") Integer id) {
        try {
            brandService.deleteBrand(id);
            return Response.ok(ApiResponse.success("Brand deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
