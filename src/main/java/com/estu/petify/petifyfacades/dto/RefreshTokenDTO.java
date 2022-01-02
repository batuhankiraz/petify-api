package com.estu.petify.petifyfacades.dto;

public class RefreshTokenDTO {

    private String refreshToken;
    private String username;

    public RefreshTokenDTO() {
        // Empty Constructor.
    }

    public RefreshTokenDTO(String refreshToken, String username) {
        this.refreshToken = refreshToken;
        this.username = username;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
