package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.UserAddress;
import com.nextcart.service.UserAddressService;
import com.nextcart.serviceImpl.UserAddressServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/user-addresses")
@Produces(MediaType.APPLICATION_JSON)
public class UserAddressController {
    private final UserAddressService userAddressService = new UserAddressServiceImpl();

    @GET
    @Path("/user/{userId}")
    public Response getAddressesByUserId(@PathParam("userId") Integer userId) {
        try {
            List<UserAddress> addresses = userAddressService.getAddressesByUserId(userId);
            return Response.ok(ApiResponse.success(addresses)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getAddressById(@PathParam("id") Integer id) {
        try {
            Optional<UserAddress> address = userAddressService.getAddressById(id);
            if (address.isPresent()) {
                return Response.ok(ApiResponse.success(address.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Address not found"))
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
    public Response createAddress(UserAddress address) {
        try {
            UserAddress createdAddress = userAddressService.createAddress(address);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdAddress))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAddress(@PathParam("id") Integer id, UserAddress address) {
        try {
            address.setAddressId(id);
            UserAddress updatedAddress = userAddressService.updateAddress(address);
            return Response.ok(ApiResponse.success(updatedAddress)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") Integer id) {
        try {
            boolean deleted = userAddressService.deleteAddress(id);
            if (deleted) {
                return Response.ok(ApiResponse.success("Address deleted successfully")).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Address not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
