package com.clerk.schema.config;

import java.util.Arrays;
import java.util.List;

public class ClerkConfig {
    private String secretKey;
    private List<String> authorizedParties;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public List<String> getAuthorizedParties() {
        return authorizedParties;
    }

    public void setAuthorizedParties(String authorizedParties) {
        this.authorizedParties = Arrays.asList(authorizedParties.split(","));
    }
}
