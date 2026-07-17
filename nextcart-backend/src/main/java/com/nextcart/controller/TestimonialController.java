package com.nextcart.controller;

import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Testimonial;
import com.nextcart.service.TestimonialService;
import com.nextcart.serviceImpl.TestimonialServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/testimonials")
@Produces(MediaType.APPLICATION_JSON)
public class TestimonialController {
    private final TestimonialService testimonialService = new TestimonialServiceImpl();

    @GET
    public Response getAllTestimonials() {
        try {
            List<Testimonial> testimonials = testimonialService.getAllTestimonials();
            return Response.ok(ApiResponse.success(testimonials)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/active")
    public Response getActiveTestimonials() {
        try {
            List<Testimonial> testimonials = testimonialService.getActiveTestimonials();
            return Response.ok(ApiResponse.success(testimonials)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getTestimonialById(@PathParam("id") Integer id) {
        try {
            Optional<Testimonial> testimonial = testimonialService.getTestimonialById(id);
            if (testimonial.isPresent()) {
                return Response.ok(ApiResponse.success(testimonial.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Testimonial not found")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTestimonial(Testimonial testimonial) {
        try {
            Testimonial created = testimonialService.createTestimonial(testimonial);
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
    public Response updateTestimonial(@PathParam("id") Integer id, Testimonial testimonial) {
        try {
            testimonial.setTestimonialId(id);
            Testimonial updated = testimonialService.updateTestimonial(testimonial);
            return Response.ok(ApiResponse.success(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteTestimonial(@PathParam("id") Integer id) {
        try {
            testimonialService.deleteTestimonial(id);
            return Response.ok(ApiResponse.success("Testimonial deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage())).build();
        }
    }
}
