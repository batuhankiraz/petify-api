package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.model.user.RefreshTokenModel;
import com.estu.petify.petifycore.repository.RefreshTokenRepository;
import com.estu.petify.petifycore.service.PetifyRefreshTokenService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service("petifyRefreshTokenService")
@Transactional
public class DefaultPetifyRefreshTokenService implements PetifyRefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshTokenModel generateRefreshToken() {
        RefreshTokenModel refreshTokenModel = new RefreshTokenModel();
        refreshTokenModel.setToken(UUID.randomUUID().toString());
        refreshTokenModel.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshTokenModel);
    }

    @Override
    public void validateRefreshToken(final String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new JwtException("Invalid Refresh Token."));
    }

    @Override
    public void deleteRefreshToken(final String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
