package com.estu.petify.petifycore.repository;

import com.estu.petify.petifycore.model.MediaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<MediaModel, Long> {

    Optional<MediaModel> findByName(final String name);

}
