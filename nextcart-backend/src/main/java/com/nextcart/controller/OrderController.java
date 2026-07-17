package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Order;
import com.nextcart.service.OrderService;
import com.nextcart.serviceImpl.OrderServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {
    private final OrderService orderService = new OrderServiceImpl();

    @GET
    public Response getAllOrders(@QueryParam("page") @DefaultValue("1") int page,
                                  @QueryParam("size") @DefaultValue("20") int size) {
        try {
            List<Order> orders = orderService.getAllOrders(page, size);
            return Response.ok(ApiResponse.success(orders)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}")
    public Response getOrdersByUserId(@PathParam("userId") Integer userId,
                                       @QueryParam("page") @DefaultValue("1") int page,
                                       @QueryParam("size") @DefaultValue("20") int size) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId, page, size);
            return Response.ok(ApiResponse.success(orders)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") Integer id) {
        try {
            Optional<Order> order = orderService.getOrderById(id);
            if (order.isPresent()) {
                return Response.ok(ApiResponse.success(order.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Order not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/number/{orderNumber}")
    public Response getOrderByOrderNumber(@PathParam("orderNumber") String orderNumber) {
        try {
            Optional<Order> order = orderService.getOrderByOrderNumber(orderNumber);
            if (order.isPresent()) {
                return Response.ok(ApiResponse.success(order.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Order not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Order order) {
        try {
            Order createdOrder = orderService.createOrder(order);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdOrder))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/create-from-cart")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrderFromCart(Map<String, Object> request) {
        try {
            Integer userId = (Integer) request.get("userId");
            Integer addressId = (Integer) request.get("addressId");
            Integer couponId = (Integer) request.get("couponId");
            
            Order order = orderService.createOrderFromCart(userId, addressId, couponId);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(order))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/payment-status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePaymentStatus(@PathParam("id") Integer id, Map<String, String> request) {
        try {
            String paymentStatus = request.get("paymentStatus");
            Order updatedOrder = orderService.updatePaymentStatus(id, paymentStatus);
            if (updatedOrder != null) {
                return Response.ok(ApiResponse.success(updatedOrder)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Order not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/order-status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrderStatus(@PathParam("id") Integer id, Map<String, String> request) {
        try {
            String orderStatus = request.get("orderStatus");
            Order updatedOrder = orderService.updateOrderStatus(id, orderStatus);
            if (updatedOrder != null) {
                return Response.ok(ApiResponse.success(updatedOrder)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Order not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
