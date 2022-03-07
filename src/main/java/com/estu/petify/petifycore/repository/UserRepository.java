package com.estu.petify.petifycore.repository;

import com.estu.petify.petifycore.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    List<UserModel> findAll();

    Optional<UserModel> findByUsername(final String username);

    @Transactional
    @Modifying
    @Query("select u from UserModel u where u.eMail=:email")
    List<UserModel> findUserModelByEMail(final String email);

    @Transactional
    @Modifying
    @Query("delete from UserModel user where user.id=:id")
    void deleteUserModelById(@Param("id") final String id);

    Optional<UserModel> findByToken(final String refreshToken);

}
