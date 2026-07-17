package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.TicketMessage;
import com.nextcart.service.TicketMessageService;
import com.nextcart.serviceImpl.TicketMessageServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/ticket-messages")
@Produces(MediaType.APPLICATION_JSON)
public class TicketMessageController {
    private final TicketMessageService ticketMessageService = new TicketMessageServiceImpl();

    @GET
    @Path("/ticket/{ticketId}")
    public Response getMessagesByTicketId(@PathParam("ticketId") Integer ticketId) {
        try {
            List<TicketMessage> messages = ticketMessageService.getMessagesByTicketId(ticketId);
            return Response.ok(ApiResponse.success(messages)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getMessageById(@PathParam("id") Integer id) {
        try {
            Optional<TicketMessage> message = ticketMessageService.getMessageById(id);
            
            if (message.isPresent()) {
                return Response.ok(ApiResponse.success(message.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Message not found"))
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
    public Response createMessage(TicketMessage ticketMessage) {
        try {
            TicketMessage createdMessage = ticketMessageService.createMessage(ticketMessage);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdMessage))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
