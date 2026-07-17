package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Offer;
import com.nextcart.service.OfferService;
import com.nextcart.serviceImpl.OfferServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/offers")
@Produces(MediaType.APPLICATION_JSON)
public class OfferController {
    private final OfferService offerService = new OfferServiceImpl();

    @GET
    public Response getAllOffers() {
        try {
            List<Offer> offers = offerService.getAllOffers();
            return Response.ok(ApiResponse.success(offers)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveOffers() {
        try {
            List<Offer> offers = offerService.getActiveOffers();
            return Response.ok(ApiResponse.success(offers)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getOfferById(@PathParam("id") Integer id) {
        try {
            Optional<Offer> offer = offerService.getOfferById(id);
            
            if (offer.isPresent()) {
                return Response.ok(ApiResponse.success(offer.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Offer not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOffer(Offer offer) {
        try {
            Offer createdOffer = offerService.createOffer(offer);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdOffer))
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
    public Response updateOffer(@PathParam("id") Integer id, Offer offer) {
        try {
            offer.setOfferId(id);
            Offer updatedOffer = offerService.updateOffer(offer);
            return Response.ok(ApiResponse.success(updatedOffer)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteOffer(@PathParam("id") Integer id) {
        try {
            offerService.deleteOffer(id);
            return Response.ok(ApiResponse.success("Offer deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
