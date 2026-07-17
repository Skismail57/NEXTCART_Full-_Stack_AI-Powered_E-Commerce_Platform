package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Category;
import com.nextcart.service.CategoryService;
import com.nextcart.serviceImpl.CategoryServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryController {
    private final CategoryService categoryService = new CategoryServiceImpl();

    @GET
    public Response getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return Response.ok(ApiResponse.success(categories)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") Integer id) {
        try {
            Optional<Category> category = categoryService.getCategoryById(id);
            
            if (category.isPresent()) {
                return Response.ok(ApiResponse.success(category.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Category not found"))
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
    public Response getCategoryBySlug(@PathParam("slug") String slug) {
        try {
            Optional<Category> category = categoryService.getCategoryBySlug(slug);
            
            if (category.isPresent()) {
                return Response.ok(ApiResponse.success(category.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Category not found"))
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
    public Response createCategory(Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdCategory))
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
    public Response updateCategory(@PathParam("id") Integer id, Category category) {
        try {
            category.setCategoryId(id);
            Category updatedCategory = categoryService.updateCategory(category);
            return Response.ok(ApiResponse.success(updatedCategory)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteCategory(@PathParam("id") Integer id) {
        try {
            categoryService.deleteCategory(id);
            return Response.ok(ApiResponse.success("Category deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
