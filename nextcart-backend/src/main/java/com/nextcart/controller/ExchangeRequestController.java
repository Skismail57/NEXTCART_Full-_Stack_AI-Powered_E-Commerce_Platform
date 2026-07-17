package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.ExchangeRequest;
import com.nextcart.service.ExchangeRequestService;
import com.nextcart.serviceImpl.ExchangeRequestServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/exchange-requests")
@Produces(MediaType.APPLICATION_JSON)
public class ExchangeRequestController {
    private final ExchangeRequestService exchangeRequestService = new ExchangeRequestServiceImpl();

    @GET
    @Path("/admin")
    public Response getAllExchangeRequests() {
        try {
            List<ExchangeRequest> requests = exchangeRequestService.getAllExchangeRequests();
            return Response.ok(ApiResponse.success(requests)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/admin/status/{status}")
    public Response getExchangeRequestsByStatus(@PathParam("status") String status) {
        try {
            List<ExchangeRequest> requests = exchangeRequestService.getExchangeRequestsByStatus(status);
            return Response.ok(ApiResponse.success(requests)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getExchangeRequestById(@PathParam("id") Integer id) {
        try {
            Optional<ExchangeRequest> request = exchangeRequestService.getExchangeRequestById(id);
            
            if (request.isPresent()) {
                return Response.ok(ApiResponse.success(request.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Exchange request not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createExchangeRequest(ExchangeRequest exchangeRequest) {
        try {
            ExchangeRequest createdRequest = exchangeRequestService.createExchangeRequest(exchangeRequest);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdRequest))
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
    public Response updateExchangeRequest(@PathParam("id") Integer id, ExchangeRequest exchangeRequest) {
        try {
            exchangeRequest.setExchangeId(id);
            ExchangeRequest updatedRequest = exchangeRequestService.updateExchangeRequest(exchangeRequest);
            return Response.ok(ApiResponse.success(updatedRequest)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteExchangeRequest(@PathParam("id") Integer id) {
        try {
            exchangeRequestService.deleteExchangeRequest(id);
            return Response.ok(ApiResponse.success("Exchange request deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
