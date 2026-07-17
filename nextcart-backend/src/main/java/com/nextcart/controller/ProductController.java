package com.nextcart.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextcart.dto.ApiResponse;
import com.nextcart.model.Product;
import com.nextcart.model.ProductImage;
import com.nextcart.model.ProductVariant;
import com.nextcart.service.ProductService;
import com.nextcart.serviceImpl.ProductServiceImpl;
import com.nextcart.util.FileUploadUtil;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {
    private final ProductService productService = new ProductServiceImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/test")
    public Response test() {
        return Response.ok(ApiResponse.success("Hello from ProductController!")).build();
    }

    @GET
    public Response getAllProducts(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("category") Integer categoryId,
            @QueryParam("search") String search) {
        try {
            List<Product> products;
            
            if (search != null && !search.isEmpty()) {
                products = productService.searchProducts(search, page, size);
            } else if (categoryId != null) {
                products = productService.getProductsByCategory(categoryId, page, size);
            } else {
                products = productService.getAllProducts(page, size);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("products", products);
            data.put("page", page);
            data.put("size", size);

            return Response.ok(ApiResponse.success(data)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Integer id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            
            if (product.isPresent()) {
                return Response.ok(ApiResponse.success(product.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Product not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/slug/{slug}")
    public Response getProductBySlug(@PathParam("slug") String slug) {
        try {
            Optional<Product> product = productService.getProductBySlug(slug);
            
            if (product.isPresent()) {
                return Response.ok(ApiResponse.success(product.get())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiResponse.error("Product not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/featured")
    public Response getFeaturedProducts(@QueryParam("limit") @DefaultValue("8") int limit) {
        try {
            List<Product> products = productService.getFeaturedProducts(limit);
            return Response.ok(ApiResponse.success(products)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/admin/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createProduct(
            @FormDataParam("productName") String productName,
            @FormDataParam("description") String description,
            @FormDataParam("categoryId") Integer categoryId,
            @FormDataParam("subCategoryId") Integer subCategoryId,
            @FormDataParam("brandId") Integer brandId,
            @FormDataParam("sku") String sku,
            @FormDataParam("price") BigDecimal price,
            @FormDataParam("discountPrice") BigDecimal discountPrice,
            @FormDataParam("tax") BigDecimal tax,
            @FormDataParam("stock") Integer stock,
            @FormDataParam("weight") BigDecimal weight,
            @FormDataParam("warranty") String warranty,
            @FormDataParam("status") String status,
            @FormDataParam("featured") Boolean featured,
            @FormDataParam("images") List<InputStream> imageStreams,
            @FormDataParam("images") List<FormDataContentDisposition> imageDetails,
            @FormDataParam("variants") String variantsJson) {
        try {
            // Create product
            Product product = new Product();
            product.setProductName(productName);
            product.setDescription(description);
            product.setCategoryId(categoryId);
            product.setSubCategoryId(subCategoryId);
            product.setBrandId(brandId);
            product.setSku(sku);
            product.setPrice(price);
            product.setDiscountPrice(discountPrice);
            product.setTax(tax != null ? tax : BigDecimal.ZERO);
            product.setStock(stock != null ? stock : 0);
            product.setWeight(weight);
            product.setWarranty(warranty);
            product.setStatus(status != null ? status : "ACTIVE");
            product.setFeatured(featured != null ? featured : false);
            // Generate slug from product name
            product.setSlug(productName.toLowerCase().replaceAll("\\s+", "-").replaceAll("[^a-z0-9-]", ""));

            // Process images
            List<ProductImage> productImages = new ArrayList<>();
            if (imageStreams != null && !imageStreams.isEmpty()) {
                for (int i = 0; i < imageStreams.size(); i++) {
                    InputStream is = imageStreams.get(i);
                    FormDataContentDisposition fdcd = imageDetails.get(i);
                    String fileName = FileUploadUtil.uploadFile(is, fdcd.getFileName());
                    ProductImage image = new ProductImage();
                    image.setImageUrl("/uploads/" + fileName);
                    image.setDisplayOrder(i);
                    image.setIsPrimary(i == 0);
                    productImages.add(image);
                }
            }

            // Process variants
            List<ProductVariant> productVariants = new ArrayList<>();
            if (variantsJson != null && !variantsJson.trim().isEmpty()) {
                productVariants = objectMapper.readValue(variantsJson, new TypeReference<List<ProductVariant>>() {});
            }

            Product createdProduct = productService.createProduct(product, productImages, productVariants);

            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.success(createdProduct))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") Integer id, Product product) {
        try {
            product.setProductId(id);
            Product updatedProduct = productService.updateProduct(product);
            return Response.ok(ApiResponse.success(updatedProduct)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    public Response deleteProduct(@PathParam("id") Integer id) {
        try {
            productService.deleteProduct(id);
            return Response.ok(ApiResponse.success("Product deleted successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }
    }
}
