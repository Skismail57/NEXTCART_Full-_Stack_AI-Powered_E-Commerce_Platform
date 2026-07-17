package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestController {

    @GET
    public Response test() {
        return Response.ok(ApiResponse.success("Hello from TestController!")).build();
    }
}
