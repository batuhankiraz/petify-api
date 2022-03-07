package com.estu.petify.petifycore.repository;

import com.estu.petify.petifycore.model.AdvertiseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PetifyAdveritiseRepository extends JpaRepository<AdvertiseModel, Long> {

    List<AdvertiseModel> findAll();

    Optional<AdvertiseModel> findById(final String id);

    @Transactional
    @Modifying
    @Query("select a from AdvertiseModel a where a.advertiserName=:username")
    List<AdvertiseModel> findAdvertiseModelByUsername(final String username);

    void deleteById(final String id);

}
