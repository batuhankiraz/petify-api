package com.estu.petify.petifycore.repository;

import com.estu.petify.petifycore.model.VerificationTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationTokenModel, Long> {

    Optional<VerificationTokenModel> findByToken(final String token);

    @Transactional
    @Modifying
    @Query("delete from VerificationTokenModel token where token.user.id=:id")
    void deleteVerificationTokenModelByUserId(final String id);

}
