package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.FlashSale;
import com.nextcart.service.FlashSaleService;
import com.nextcart.serviceImpl.FlashSaleServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/flash-sales")
@Produces(MediaType.APPLICATION_JSON)
public class FlashSaleController {
    private final FlashSaleService flashSaleService = new FlashSaleServiceImpl();

    @GET
    public Response getAllFlashSales() {
        try {
            List<FlashSale> flashSales = flashSaleService.getAllFlashSales();
            return Response.ok(ApiResponse.success(flashSales)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveFlashSales() {
        try {
            List<FlashSale> flashSales = flashSaleService.getActiveFlashSales();
            return Response.ok(ApiResponse.success(flashSales)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getFlashSaleById(@PathParam("id") Integer id) {
        try {
            Optional<FlashSale> flashSale = flashSaleService.getFlashSaleById(id);
            if (flashSale.isPresent()) {
                return Response.ok(ApiResponse.success(flashSale.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Flash sale not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFlashSale(FlashSale flashSale) {
        try {
            FlashSale created = flashSaleService.createFlashSale(flashSale);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(created)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/admin/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFlashSale(@PathParam("id") Integer id, FlashSale flashSale) {
        try {
            flashSale.setFlashSaleId(id);
            FlashSale updated = flashSaleService.updateFlashSale(flashSale);
            return Response.ok(ApiResponse.success(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteFlashSale(@PathParam("id") Integer id) {
        try {
            flashSaleService.deleteFlashSale(id);
            return Response.ok(ApiResponse.success("Flash sale deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }
}
