package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.model.AdvertiseModel;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.repository.PetifyAdveritiseRepository;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.service.PetifyAdvertiseService;
import com.estu.petify.petifyfacades.dto.AdvertiseDTO;
import com.estu.petify.petifyfacades.dto.UpdateAdvertiseDTO;
import com.estu.petify.petifyfacades.mapper.AdvertiseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service("petifyAdvertiseService")
@RequiredArgsConstructor
@Slf4j
public class DefaultPetifyAdvertiseService implements PetifyAdvertiseService {

    private final PetifyAdveritiseRepository petifyAdveritiseRepository;
    private final UserRepository userRepository;

    private final AdvertiseMapper advertiseMapper = Mappers.getMapper(AdvertiseMapper.class);


    @Override
    public List<AdvertiseModel> getAllAdverts() {
        return petifyAdveritiseRepository.findAll();
    }


    @Override
    public AdvertiseModel advertise(final AdvertiseDTO advertiseDTO) {

        final UserModel userByJwtToken = userRepository.findByToken(advertiseDTO.getToken()).orElseThrow();
        final AdvertiseModel advertiseModel = advertiseMapper.mapToModel(advertiseDTO, userByJwtToken);
        //setUserInformationForAdvertise(advertiseModel, userByJwtToken);
        petifyAdveritiseRepository.save(advertiseModel);

        return advertiseModel;
    }


    @Override
    public AdvertiseModel getAdvertDetail(final String id) {
        return petifyAdveritiseRepository
                .findById(id)
                .orElseThrow();
    }


    @Override
    public AdvertiseModel updateAdvertise(final UpdateAdvertiseDTO updateAdvertiseDTO) {

        final String id = updateAdvertiseDTO.getId();
        final String title = updateAdvertiseDTO.getTitle();
        final String description = updateAdvertiseDTO.getDescription();
        final String price = updateAdvertiseDTO.getPrice();
        final String country = updateAdvertiseDTO.getCountry();
        final String city = updateAdvertiseDTO.getCity();
        final String town = updateAdvertiseDTO.getTown();
        final String petPreferences = updateAdvertiseDTO.getPetPreferences();

        final AdvertiseModel advertiseModel = petifyAdveritiseRepository.findById(id).orElseThrow();

        try {
            if (StringUtils.hasText(title)) {
                advertiseModel.setTitle(title);
            }
            if (StringUtils.hasText(description)) {
                advertiseModel.setDescription(description);
            }
            if (StringUtils.hasText(price)) {
                advertiseModel.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
            }
            if (StringUtils.hasText(country)) {
                advertiseModel.setCountry(country);
            }
            if (StringUtils.hasText(city)) {
                advertiseModel.setCity(city);
            }
            if (StringUtils.hasText(town)) {
                advertiseModel.setTown(town);
            }
            if (StringUtils.hasText(petPreferences)) {
                advertiseModel.setPetPreferencesList(createPetPreferencesList(petPreferences));
            }

            petifyAdveritiseRepository.save(advertiseModel);
        } catch (final Exception e) {
            log.error("ERR: Unable to update Advertise with id: {}. | Cause{}", id, e.getCause().getMessage());
        }

        return advertiseModel;
    }


    @Override
    public List<AdvertiseModel> getUserAdvertsByUsername(final String username) {

        if (StringUtils.hasText(username)) {
            return petifyAdveritiseRepository.findAdvertiseModelByUsername(username);
        }
        return Collections.emptyList();
    }


    @Transactional
    @Override
    public void removeAdvertiseById(final String id) {
        petifyAdveritiseRepository
                .deleteById(id);
    }


    private void setUserInformationForAdvertise(final AdvertiseModel advertiseModel, final UserModel userByJwtToken) {

        final String userFullName = userByJwtToken.getFirstName().toUpperCase() + " " + userByJwtToken.getLastName().toUpperCase();

        advertiseModel.setUsername(userByJwtToken.getUsername());
        advertiseModel.setName(userFullName);
        advertiseModel.setPhone(userByJwtToken.getPhoneNumber());
        advertiseModel.setAddress(userByJwtToken.getAddress());

    }

    private List<String> createPetPreferencesList(final String value) {

        return StringUtils.hasText(value) ? Arrays.asList(formatPetPrefences(value).split(",")) : Collections.emptyList();
    }

    private String formatPetPrefences(final String value) {

        return value.replace(" ", ",")
                .replace("-", ",")
                .replace("/", ",");
    }
}
