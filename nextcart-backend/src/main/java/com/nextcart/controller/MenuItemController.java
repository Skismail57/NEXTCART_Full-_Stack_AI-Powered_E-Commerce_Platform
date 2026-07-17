package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.MenuItem;
import com.nextcart.service.MenuItemService;
import com.nextcart.serviceImpl.MenuItemServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/menu-items")
@Produces(MediaType.APPLICATION_JSON)
public class MenuItemController {
    private final MenuItemService menuItemService = new MenuItemServiceImpl();

    @GET
    public Response getAllMenuItems() {
        try {
            List<MenuItem> menuItems = menuItemService.getAllMenuItems();
            return Response.ok(ApiResponse.success(menuItems)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveMenuItems() {
        try {
            List<MenuItem> menuItems = menuItemService.getActiveMenuItems();
            return Response.ok(ApiResponse.success(menuItems)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/type/{menuType}")
    public Response getMenuItemsByMenuType(@PathParam("menuType") String menuType) {
        try {
            List<MenuItem> menuItems = menuItemService.getMenuItemsByMenuType(menuType);
            return Response.ok(ApiResponse.success(menuItems)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/root/{menuType}")
    public Response getRootMenuItems(@PathParam("menuType") String menuType) {
        try {
            List<MenuItem> menuItems = menuItemService.getRootMenuItems(menuType);
            return Response.ok(ApiResponse.success(menuItems)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/parent/{parentId}")
    public Response getMenuItemsByParentId(@PathParam("parentId") Integer parentId) {
        try {
            List<MenuItem> menuItems = menuItemService.getMenuItemsByParentId(parentId);
            return Response.ok(ApiResponse.success(menuItems)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getMenuItemById(@PathParam("id") Integer id) {
        try {
            Optional<MenuItem> menuItem = menuItemService.getMenuItemById(id);
            if (menuItem.isPresent()) {
                return Response.ok(ApiResponse.success(menuItem.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Menu item not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMenuItem(MenuItem menuItem) {
        try {
            MenuItem created = menuItemService.createMenuItem(menuItem);
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
    public Response updateMenuItem(@PathParam("id") Integer id, MenuItem menuItem) {
        try {
            menuItem.setMenuItemId(id);
            MenuItem updated = menuItemService.updateMenuItem(menuItem);
            return Response.ok(ApiResponse.success(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteMenuItem(@PathParam("id") Integer id) {
        try {
            menuItemService.deleteMenuItem(id);
            return Response.ok(ApiResponse.success("Menu item deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }
}
