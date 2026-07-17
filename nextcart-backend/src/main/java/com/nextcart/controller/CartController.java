package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.CartItem;
import com.nextcart.service.CartService;
import com.nextcart.serviceImpl.CartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Path("/cart")
@Produces(MediaType.APPLICATION_JSON)
public class CartController {
    private final CartService cartService = new CartServiceImpl();

    @GET
    public Response getCart(@Context HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            List<CartItem> cartItems = cartService.getCartItemsByCartId(
                    cartService.getOrCreateCartByUserId(userId).getCartId()
            );
            return Response.ok(ApiResponse.success(cartItems)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/total")
    public Response getCartTotal(@Context HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            Integer cartId = cartService.getOrCreateCartByUserId(userId).getCartId();
            BigDecimal total = cartService.getCartTotal(cartId);
            int count = cartService.getCartItemCount(cartId);
            return Response.ok(ApiResponse.success(Map.of("total", total, "count", count))).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCartItem(@Context HttpServletRequest request, Map<String, Object> requestBody) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            Integer productId = (Integer) requestBody.get("productId");
            Integer variantId = (Integer) requestBody.get("variantId");
            Integer quantity = (Integer) requestBody.getOrDefault("quantity", 1);
            
            CartItem cartItem = cartService.addCartItem(userId, productId, variantId, quantity);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(cartItem))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/item/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCartItemQuantity(@PathParam("id") Integer id, Map<String, Object> request) {
        try {
            Integer quantity = (Integer) request.get("quantity");
            CartItem cartItem = cartService.updateCartItemQuantity(id, quantity);
            if (cartItem != null) {
                return Response.ok(ApiResponse.success(cartItem)).build();
            } else {
                return Response.ok(ApiResponse.success("Cart item removed")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/item/{id}")
    public Response removeCartItem(@PathParam("id") Integer id) {
        try {
            boolean deleted = cartService.removeCartItem(id);
            if (deleted) {
                return Response.ok(ApiResponse.success("Cart item removed")).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Cart item not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    public Response clearCart(@Context HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getAttribute("userId");
            Integer cartId = cartService.getOrCreateCartByUserId(userId).getCartId();
            cartService.clearCart(cartId);
            return Response.ok(ApiResponse.success("Cart cleared")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
