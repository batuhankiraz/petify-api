package com.estu.petify.petifyfacades.response;

import lombok.Builder;
import java.time.Instant;

@Builder
public class AuthenticationResponse {

    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;

    public AuthenticationResponse() {
        //Empty Constructor
    }

    public AuthenticationResponse(String authenticationToken, String refreshToken, Instant expiresAt, String username) {
        this.authenticationToken = authenticationToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.username = username;

    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
