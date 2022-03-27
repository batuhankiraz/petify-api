package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.model.RefreshTokenModel;
import com.estu.petify.petifycore.repository.RefreshTokenRepository;
import com.estu.petify.petifycore.service.PetifyRefreshTokenService;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service("petifyRefreshTokenService")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DefaultPetifyRefreshTokenService implements PetifyRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshTokenModel generateRefreshToken() {

        final RefreshTokenModel refreshTokenModel = new RefreshTokenModel();
        refreshTokenModel.setToken(UUID.randomUUID().toString());
        refreshTokenModel.setCreatedDate(Instant.now());

        log.info("INFO: Refresh Token created successfully. [ {} ]", refreshTokenModel.getToken());
        return refreshTokenRepository.save(refreshTokenModel);
    }

    @Override
    public void validateRefreshToken(final String token) {

        refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new JwtException("Invalid Refresh Token."));
    }

    @Override
    public void deleteRefreshToken(final String token) {

        refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public RefreshTokenModel getRefreshTokenByRefreshToken(String refreshToken) {

        return refreshTokenRepository.findByToken(refreshToken).orElseThrow();
    }
}
