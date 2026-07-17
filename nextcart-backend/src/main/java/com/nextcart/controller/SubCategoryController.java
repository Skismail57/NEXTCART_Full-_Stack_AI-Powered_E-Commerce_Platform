package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.SubCategory;
import com.nextcart.service.SubCategoryService;
import com.nextcart.serviceImpl.SubCategoryServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/subcategories")
@Produces(MediaType.APPLICATION_JSON)
public class SubCategoryController {
    private final SubCategoryService subCategoryService = new SubCategoryServiceImpl();

    @GET
    public Response getAllSubCategories() {
        try {
            List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
            return Response.ok(ApiResponse.success(subCategories)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/category/{categoryId}")
    public Response getSubCategoriesByCategoryId(@PathParam("categoryId") Integer categoryId) {
        try {
            List<SubCategory> subCategories = subCategoryService.getSubCategoriesByCategoryId(categoryId);
            return Response.ok(ApiResponse.success(subCategories)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getSubCategoryById(@PathParam("id") Integer id) {
        try {
            Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(id);
            if (subCategory.isPresent()) {
                return Response.ok(ApiResponse.success(subCategory.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Sub-category not found"))
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
    public Response createSubCategory(SubCategory subCategory) {
        try {
            SubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdSubCategory))
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
    public Response updateSubCategory(@PathParam("id") Integer id, SubCategory subCategory) {
        try {
            subCategory.setSubCategoryId(id);
            SubCategory updatedSubCategory = subCategoryService.updateSubCategory(subCategory);
            return Response.ok(ApiResponse.success(updatedSubCategory)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteSubCategory(@PathParam("id") Integer id) {
        try {
            boolean deleted = subCategoryService.deleteSubCategory(id);
            if (deleted) {
                return Response.ok(ApiResponse.success("Sub-category deleted successfully")).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Sub-category not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
