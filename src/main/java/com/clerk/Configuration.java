package com.clerk;

import com.clerk.schema.config.ClerkConfig;

public class Configuration extends io.dropwizard.core.Configuration {

    private ClerkConfig clerk;

    public ClerkConfig getClerk() {
        return clerk;
    }

    public void setClerk(ClerkConfig clerk) {
        this.clerk = clerk;
    }
}
