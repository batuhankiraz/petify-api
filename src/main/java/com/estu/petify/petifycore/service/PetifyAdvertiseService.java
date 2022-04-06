package com.estu.petify.petifycore.service;

import com.estu.petify.petifycore.model.AdvertiseModel;
import com.estu.petify.petifyfacades.dto.AdvertiseDTO;
import com.estu.petify.petifyfacades.dto.UpdateAdvertiseDTO;

import java.util.List;

public interface PetifyAdvertiseService {
    List<AdvertiseModel> getAllAdverts();

    AdvertiseModel advertise(final AdvertiseDTO advertiseDTO);

    AdvertiseModel getAdvertDetail(final String id);

    AdvertiseModel updateAdvertise(final UpdateAdvertiseDTO updateAdvertiseDTO);

    List<AdvertiseModel> getUserAdvertsByUsername(final String username);

    void removeAdvertiseById(final String id);
}
