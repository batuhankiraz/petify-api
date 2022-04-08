package com.estu.petify.petifycore.repository;

import com.estu.petify.petifycore.model.RefreshTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
    Optional<RefreshTokenModel> findByToken(final String token);

    void deleteByToken(final String token);
}
