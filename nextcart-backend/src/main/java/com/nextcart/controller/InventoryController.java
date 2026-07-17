package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Inventory;
import com.nextcart.service.InventoryService;
import com.nextcart.serviceImpl.InventoryServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
public class InventoryController {
    private final InventoryService inventoryService = new InventoryServiceImpl();

    @GET
    @Path("/admin")
    public Response getAllInventories() {
        try {
            List<Inventory> inventories = inventoryService.getAllInventories();
            return Response.ok(ApiResponse.success(inventories)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/admin/low-stock")
    public Response getLowStockInventories(@QueryParam("threshold") @DefaultValue("10") Integer threshold) {
        try {
            List<Inventory> inventories = inventoryService.getLowStockInventories(threshold);
            return Response.ok(ApiResponse.success(inventories)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/product/{productId}")
    public Response getInventoryByProductId(@PathParam("productId") Integer productId) {
        try {
            Optional<Inventory> inventory = inventoryService.getInventoryByProductId(productId);
            
            if (inventory.isPresent()) {
                return Response.ok(ApiResponse.success(inventory.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Inventory not found"))
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
    public Response createInventory(Inventory inventory) {
        try {
            Inventory createdInventory = inventoryService.createInventory(inventory);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdInventory))
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
    public Response updateInventory(@PathParam("id") Integer id, Inventory inventory) {
        try {
            inventory.setInventoryId(id);
            Inventory updatedInventory = inventoryService.updateInventory(inventory);
            return Response.ok(ApiResponse.success(updatedInventory)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteInventory(@PathParam("id") Integer id) {
        try {
            inventoryService.deleteInventory(id);
            return Response.ok(ApiResponse.success("Inventory deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
