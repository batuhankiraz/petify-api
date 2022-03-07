package com.estu.petify.petifycore.service;

import com.estu.petify.petifycore.model.RefreshTokenModel;

public interface PetifyRefreshTokenService {

    RefreshTokenModel generateRefreshToken();

    void validateRefreshToken(final String token);

    void deleteRefreshToken(final String token);

    RefreshTokenModel getRefreshTokenByRefreshToken(String refreshToken);
}
