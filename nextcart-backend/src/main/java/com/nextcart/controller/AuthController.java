package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.service.UserService;
import com.nextcart.serviceImpl.UserServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {
    private final UserService userService = new UserServiceImpl();

    @POST
    @Path("/register")
    public Response register(Map<String, String> request) {
        try {
            String firstName = request.get("firstName");
            String lastName = request.get("lastName");
            String email = request.get("email");
            String password = request.get("password");

            if (firstName == null || lastName == null || email == null || password == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(ApiResponse.error("Missing required fields"))
                        .build();
            }

            userService.registerUser(firstName, lastName, email, password);
            return Response.ok(ApiResponse.success("Registration successful", null)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            if (email == null || password == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(ApiResponse.error("Missing email or password"))
                        .build();
            }

            Map<String, Object> result = userService.login(email, password);
            return Response.ok(ApiResponse.success(result)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
