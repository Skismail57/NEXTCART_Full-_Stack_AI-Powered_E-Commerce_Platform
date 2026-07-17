package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.FeaturedCollection;
import com.nextcart.service.FeaturedCollectionService;
import com.nextcart.serviceImpl.FeaturedCollectionServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/featured-collections")
@Produces(MediaType.APPLICATION_JSON)
public class FeaturedCollectionController {
    private final FeaturedCollectionService collectionService = new FeaturedCollectionServiceImpl();

    @GET
    public Response getAllCollections() {
        try {
            List<FeaturedCollection> collections = collectionService.getAllCollections();
            return Response.ok(ApiResponse.success(collections)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveCollections() {
        try {
            List<FeaturedCollection> collections = collectionService.getActiveCollections();
            return Response.ok(ApiResponse.success(collections)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getCollectionById(@PathParam("id") Integer id) {
        try {
            Optional<FeaturedCollection> collection = collectionService.getCollectionById(id);
            if (collection.isPresent()) {
                return Response.ok(ApiResponse.success(collection.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Featured collection not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/slug/{slug}")
    public Response getCollectionBySlug(@PathParam("slug") String slug) {
        try {
            Optional<FeaturedCollection> collection = collectionService.getCollectionBySlug(slug);
            if (collection.isPresent()) {
                return Response.ok(ApiResponse.success(collection.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Featured collection not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCollection(FeaturedCollection collection) {
        try {
            FeaturedCollection created = collectionService.createCollection(collection);
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
    public Response updateCollection(@PathParam("id") Integer id, FeaturedCollection collection) {
        try {
            collection.setCollectionId(id);
            FeaturedCollection updated = collectionService.updateCollection(collection);
            return Response.ok(ApiResponse.success(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteCollection(@PathParam("id") Integer id) {
        try {
            collectionService.deleteCollection(id);
            return Response.ok(ApiResponse.success("Featured collection deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }
}
