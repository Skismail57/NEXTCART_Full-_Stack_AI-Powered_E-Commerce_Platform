package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Wishlist;
import com.nextcart.service.WishlistService;
import com.nextcart.serviceImpl.WishlistServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/wishlist")
@Produces(MediaType.APPLICATION_JSON)
public class WishlistController {
    private final WishlistService wishlistService = new WishlistServiceImpl();

    @GET
    public Response getWishlist(@Context HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            List<Wishlist> wishlistItems = wishlistService.getWishlistByUserId(userId);
            return Response.ok(ApiResponse.success(wishlistItems)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/product/{productId}")
    public Response isInWishlist(@Context HttpServletRequest request, @PathParam("productId") Integer productId) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            boolean exists = wishlistService.isInWishlist(userId, productId);
            return Response.ok(ApiResponse.success(exists)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToWishlist(@Context HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            Integer productId = (Integer) requestBody.get("productId");
            
            Wishlist wishlist = new Wishlist();
            wishlist.setUserId(userId);
            wishlist.setProductId(productId);
            
            Wishlist createdWishlist = wishlistService.addToWishlist(wishlist);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdWishlist))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeFromWishlist(@PathParam("id") Integer id) {
        try {
            boolean deleted = wishlistService.removeFromWishlist(id);
            if (deleted) {
                return Response.ok(ApiResponse.success("Removed from wishlist")).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Wishlist item not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/product/{productId}")
    public Response removeFromWishlistByProduct(@Context HttpServletRequest request, @PathParam("productId") Integer productId) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            boolean deleted = wishlistService.removeFromWishlistByUserIdAndProductId(userId, productId);
            if (deleted) {
                return Response.ok(ApiResponse.success("Removed from wishlist")).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Wishlist item not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
