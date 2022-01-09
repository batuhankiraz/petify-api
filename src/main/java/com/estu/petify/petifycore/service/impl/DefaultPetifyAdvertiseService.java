package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.model.advertising.AdvertiseModel;
import com.estu.petify.petifycore.model.user.UserModel;
import com.estu.petify.petifycore.repository.PetifyAdveritiseRepository;
import com.estu.petify.petifycore.service.PetifyAdvertiseService;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifyfacades.dto.AdvertiseDTO;
import com.estu.petify.petifystorefront.utils.PetifyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("petifyAdvertiseService")
public class DefaultPetifyAdvertiseService implements PetifyAdvertiseService {

    private static final String DD_MM_YYYY = "dd/MM/yyyy HH:mm:ss";

    @Autowired
    private PetifyAdveritiseRepository petifyAdveritiseRepository;
    @Autowired
    private PetifyAuthService petifyAuthService;
    @Autowired
    private UserService defaultUserService;

    @Override
    public AdvertiseModel advertise(final String username, AdvertiseDTO advertiseDTO) {
        final UserModel user = defaultUserService.getUserByUsername(username);
        final AdvertiseModel newAdvertise = new AdvertiseModel();
        newAdvertise.setAdvertiserName(user.getUsername());
        newAdvertise.setAdvertiserMail(user.geteMail());
        newAdvertise.setAdvertiserPhoneNumber(user.getPhoneNumber());
        newAdvertise.setCreationTime(PetifyDateUtils.getCurrentDateWithPattern(DD_MM_YYYY));
        newAdvertise.setTitle(advertiseDTO.getTitle());
        newAdvertise.setType(advertiseDTO.getType());
        newAdvertise.setDescription(advertiseDTO.getDescription());
        newAdvertise.setPetName(advertiseDTO.getPetName());
        newAdvertise.setPetDesc(advertiseDTO.getPetDesc());
        newAdvertise.setPetColor(advertiseDTO.getPetColor());
        newAdvertise.setPetFamily(advertiseDTO.getPetFamily());
        newAdvertise.setPetGender(advertiseDTO.getPetGender());
        petifyAdveritiseRepository.save(newAdvertise);
        return newAdvertise;
    }

    @Override
    public List<AdvertiseModel> getAllAdverts() {
        return petifyAdveritiseRepository.findAll();
    }

    @Override
    public AdvertiseModel getAdvertDetail(String id) {
        AdvertiseModel advertiseModel = petifyAdveritiseRepository.findById(id).orElseThrow();
        return advertiseModel;
    }

    @Override
    public AdvertiseModel updateAdvertise(final AdvertiseDTO advertiseDTO, final AdvertiseModel advertiseModel) {
        advertiseModel.setTitle(advertiseDTO.getTitle());
        advertiseModel.setType(advertiseDTO.getType());
        advertiseModel.setDescription(advertiseDTO.getDescription());
        advertiseModel.setPetName(advertiseDTO.getPetName());
        advertiseModel.setPetDesc(advertiseDTO.getPetDesc());
        advertiseModel.setPetColor(advertiseDTO.getPetColor());
        advertiseModel.setPetFamily(advertiseDTO.getPetFamily());
        advertiseModel.setPetGender(advertiseDTO.getPetGender());
        petifyAdveritiseRepository.save(advertiseModel);
        return advertiseModel;
    }

    @Override
    public List<AdvertiseModel> getCurrentUserAdverts(String username) {
        return petifyAdveritiseRepository.findAdvertiseModelByUsername(username);
    }

    @Transactional
    @Override
    public void removeAdvertiseById(String id) {
        petifyAdveritiseRepository.deleteById(id);
    }
}
