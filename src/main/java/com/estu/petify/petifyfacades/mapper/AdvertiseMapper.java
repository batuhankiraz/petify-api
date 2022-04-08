package com.estu.petify.petifyfacades.mapper;

import com.estu.petify.petifycore.model.AdvertiseModel;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifyfacades.dto.AdvertiseDTO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdvertiseMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", expression = "java(java.time.Instant.now())")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "name", source = "user.firstName")
    @Mapping(target = "phone", source = "user.phoneNumber")
    @Mapping(target = "address", source = "user.address")
    @Mapping(target = "title", source = "advertiseDTO.title")
    @Mapping(target = "description", source = "advertiseDTO.description")
    @Mapping(target = "price", source = "advertiseDTO.price")
    @Mapping(target = "country", source = "advertiseDTO.country")
    @Mapping(target = "city", source = "advertiseDTO.city")
    @Mapping(target = "town", source = "advertiseDTO.town")
    @Mapping(target = "petPreferencesList", source = "advertiseDTO.petPreferences", qualifiedByName = "petPreferences")
    AdvertiseModel mapToModel(final AdvertiseDTO advertiseDTO, final UserModel user);


    @Mapping(target = "id", source = "advertiseModel.id")
    @Mapping(target = "title", source = "advertiseModel.title")
    @Mapping(target = "description", source = "advertiseModel.description")
    @Mapping(target = "price", source = "advertiseModel.price")
    @Mapping(target = "country", source = "advertiseModel.country")
    @Mapping(target = "city", source = "advertiseModel.city")
    @Mapping(target = "town", source = "advertiseModel.town")
    @Mapping(target = "petPreferences", source = "advertiseModel.petPreferencesList", qualifiedByName = "petPreferencesList")
    AdvertiseDTO mapToDto(final AdvertiseModel advertiseModel);


    @Named("petPreferences")
    default List<String> mapToPetPreferencesList(final String petPreferences) {

        petPreferences.replaceAll(" ", ",")
                .replaceAll("-", ",")
                .replaceAll("/", ",")
                .replaceAll("|", ",");

        return StringUtils.isNotBlank(petPreferences) ? Arrays.asList(petPreferences.split(",")) : Collections.emptyList();
    }

    @Named("petPreferencesList")
    default String mapToPetPreferences(final List<String> petPreferencesList) {

        final String petPreferencesForDto = StringUtils.join(petPreferencesList, " - ");

        return StringUtils.isNotBlank(petPreferencesForDto) ? petPreferencesForDto : " ";
    }

}
