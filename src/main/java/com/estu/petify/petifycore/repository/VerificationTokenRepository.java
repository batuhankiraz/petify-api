package com.estu.petify.petifycore.repository;

import com.estu.petify.petifycore.model.VerificationTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationTokenModel, Long> {

    Optional<VerificationTokenModel> findByToken(final String token);

    void deleteByUsername(final String username);

}
