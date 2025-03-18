package com.clerk.filter;

import com.clerk.backend_api.helpers.jwks.AuthenticateRequest;
import com.clerk.backend_api.helpers.jwks.AuthenticateRequestOptions;
import com.clerk.backend_api.helpers.jwks.RequestState;
import com.clerk.schema.auth.UserPrincipal;
import com.clerk.schema.config.ClerkConfig;
import jakarta.annotation.Priority;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtRequestAuthenticationFilter implements ContainerRequestFilter {

    private final ClerkConfig clerk;

    public JwtRequestAuthenticationFilter(ClerkConfig clerk) {
        this.clerk = clerk;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        try {

            if (containerRequestContext.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS)) {
                return;
            }

            // authenticate with clerk API
            RequestState state = AuthenticateRequest.authenticateRequest(containerRequestContext.getHeaders(),
                AuthenticateRequestOptions.Builder.withSecretKey(clerk.getSecretKey()).authorizedParties(clerk.getAuthorizedParties()).build()
            );

            if (!state.isSignedIn()){
                containerRequestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                        .header("Content-Type", "application/json")
                        .entity("{\"detail\": \"" + state.reason().get().message() + "\"}")
                        .build());
            } else {
                String userId = (String) state.claims().get().get("sub");
                UserPrincipal userPrincipal = new UserPrincipal(userId);
                SecurityContext originalContext = containerRequestContext.getSecurityContext();
                containerRequestContext.setSecurityContext(
                    new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return userPrincipal;
                        }

                        @Override
                        public boolean isUserInRole(String role) {
                            // implement this for roles check
                            return false;
                        }

                        @Override
                        public boolean isSecure() {
                            return originalContext.isSecure();
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return "ClerkJwtAuthScheme";
                        }
                    }
                );
            }

        } catch (Exception e) {
            containerRequestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                    .header("Content-Type", "application/json")
                    .entity("{\"detail\": \"" + "Unable to authenticate request" + "\"}")
                    .build());
        }
    }
}
