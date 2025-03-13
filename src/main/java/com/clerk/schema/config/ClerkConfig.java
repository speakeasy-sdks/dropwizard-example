package com.clerk.schema.config;

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

    public void setAuthorizedParties(List<String> authorizedParties) {
        this.authorizedParties = authorizedParties;
    }
}
