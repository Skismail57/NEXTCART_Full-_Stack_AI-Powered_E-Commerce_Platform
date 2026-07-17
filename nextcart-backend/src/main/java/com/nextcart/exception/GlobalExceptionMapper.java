package com.nextcart.exception;

import com.nextcart.response.BaseResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        logger.error("Exception occurred: ", exception);

        BaseResponse<Void> response = new BaseResponse<>();
        response.setSuccess(false);

        if (exception instanceof ResourceNotFoundException) {
            response.setMessage(exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else if (exception instanceof UnauthorizedException) {
            response.setMessage(exception.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else if (exception instanceof ForbiddenException) {
            response.setMessage(exception.getMessage());
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else if (exception instanceof ValidationException) {
            response.setMessage(exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else if (exception instanceof FileUploadException) {
            response.setMessage(exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            response.setMessage("An unexpected error occurred");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
