package com.clerk;

import com.clerk.api.ClerkAuthExampleResource;
import com.clerk.filter.JwtRequestAuthenticationFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import java.util.EnumSet;
import org.eclipse.jetty.servlets.CrossOriginFilter;

public class Application extends io.dropwizard.core.Application<Configuration> {

    public static void main(final String[] args) throws Exception {
        new Application().run(args);
    }

    @Override
    public String getName() {
        return "clerk-dropwizard-example";
    }

    @Override
    public void initialize(final Bootstrap<Configuration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)
            )
        );
    }

    @Override
    public void run(final Configuration configuration,
                    final Environment environment) {

        final FilterRegistration.Dynamic cors =
            environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        String allowedOrigins = configuration.getClerk().getAuthorizedParties().stream()
            .reduce((a, b) -> a + "," + b).orElse("");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, allowedOrigins);
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        JwtRequestAuthenticationFilter jwtRequestAuthenticationFilter = new JwtRequestAuthenticationFilter(
            configuration.getClerk());
        environment.jersey().register(jwtRequestAuthenticationFilter);
        environment.jersey().register(ClerkAuthExampleResource.class);
    }

}
