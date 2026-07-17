package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.User;
import com.nextcart.service.UserService;
import com.nextcart.serviceImpl.UserServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    private final UserService userService = new UserServiceImpl();

    @GET
    @Path("/admin")
    public Response getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return Response.ok(ApiResponse.success(users)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Integer id) {
        try {
            Optional<User> user = userService.getUserById(id);
            
            if (user.isPresent()) {
                return Response.ok(ApiResponse.success(user.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("User not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserStatus(@PathParam("id") Integer id, User user) {
        try {
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("User not found"))
                        .build();
            }
            
            User updatedUser = existingUser.get();
            updatedUser.setStatus(user.getStatus());
            updatedUser = userService.updateUser(updatedUser);
            
            return Response.ok(ApiResponse.success(updatedUser)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteUser(@PathParam("id") Integer id) {
        try {
            userService.deleteUser(id);
            return Response.ok(ApiResponse.success("User deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
