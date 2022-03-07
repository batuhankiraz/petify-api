package com.estu.petify.petifycore.service;

import com.estu.petify.petifycore.model.AdvertiseModel;
import com.estu.petify.petifyfacades.dto.AdvertiseDTO;

import java.util.List;

public interface PetifyAdvertiseService {
    AdvertiseModel advertise(final AdvertiseDTO advertiseDTO);

    List<AdvertiseModel> getAllAdverts();

    AdvertiseModel getAdvertDetail(final String id);

    AdvertiseModel updateAdvertise(final AdvertiseDTO advertiseDTO, final AdvertiseModel advertiseModel);

    List<AdvertiseModel> getCurrentUserAdverts(final String username);

    void removeAdvertiseById(final String id);
}
