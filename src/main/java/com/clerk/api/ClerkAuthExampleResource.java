package com.clerk.api;

import com.clerk.schema.response.VerifiedJwtResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ClerkAuthExampleResource {

    @GET
    @Path("clerk_jwt")
    public VerifiedJwtResponse clerk_jwt(@Context SecurityContext securityContext) {
        return new VerifiedJwtResponse(securityContext.getUserPrincipal().getName());
    }

    @GET
    @Path("gated_data")
    public String get_gated_data() {
        return "{\"foo\":\"bar\"}";
    }

}
