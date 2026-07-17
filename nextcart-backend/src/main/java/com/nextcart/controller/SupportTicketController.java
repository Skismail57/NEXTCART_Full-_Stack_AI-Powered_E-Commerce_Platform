package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.SupportTicket;
import com.nextcart.service.SupportTicketService;
import com.nextcart.serviceImpl.SupportTicketServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/support-tickets")
@Produces(MediaType.APPLICATION_JSON)
public class SupportTicketController {
    private final SupportTicketService supportTicketService = new SupportTicketServiceImpl();

    @GET
    public Response getAllTickets(
            @QueryParam("userId") Integer userId,
            @QueryParam("assignedTo") Integer assignedTo,
            @QueryParam("status") String status) {
        try {
            List<SupportTicket> tickets;

            if (userId != null) {
                tickets = supportTicketService.getTicketsByUserId(userId);
            } else if (assignedTo != null) {
                tickets = supportTicketService.getTicketsByAssignedTo(assignedTo);
            } else if (status != null) {
                tickets = supportTicketService.getTicketsByStatus(status);
            } else {
                tickets = supportTicketService.getAllTickets();
            }

            return Response.ok(ApiResponse.success(tickets)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getTicketById(@PathParam("id") Integer id) {
        try {
            Optional<SupportTicket> ticket = supportTicketService.getTicketById(id);
            
            if (ticket.isPresent()) {
                return Response.ok(ApiResponse.success(ticket.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Ticket not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/number/{ticketNumber}")
    public Response getTicketByNumber(@PathParam("ticketNumber") String ticketNumber) {
        try {
            Optional<SupportTicket> ticket = supportTicketService.getTicketByNumber(ticketNumber);
            
            if (ticket.isPresent()) {
                return Response.ok(ApiResponse.success(ticket.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Ticket not found"))
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
    public Response createTicket(SupportTicket supportTicket) {
        try {
            // Generate unique ticket number
            supportTicket.setTicketNumber("TCKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            SupportTicket createdTicket = supportTicketService.createTicket(supportTicket);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdTicket))
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
    public Response updateTicket(@PathParam("id") Integer id, SupportTicket supportTicket) {
        try {
            supportTicket.setSupportTicketId(id);
            SupportTicket updatedTicket = supportTicketService.updateTicket(supportTicket);
            return Response.ok(ApiResponse.success(updatedTicket)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
