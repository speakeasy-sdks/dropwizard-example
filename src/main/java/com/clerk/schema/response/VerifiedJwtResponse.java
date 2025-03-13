package com.clerk.schema.response;

public class VerifiedJwtResponse {

    private final String userId;

    public VerifiedJwtResponse(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
