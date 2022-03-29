package com.estu.petify.petifycore.service;

import com.estu.petify.petifyfacades.dto.RegisterDTO;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifyfacades.dto.UpdateProfileDTO;

import java.util.List;

public interface UserService {

    List<UserModel> getUsers();

    UserModel register(final RegisterDTO newUser);

    UserModel updateProfile(final UpdateProfileDTO updateProfileDTO);

    void deleteByUsername(final String username);

    List<UserModel> getUserByEmail(final String email);
}
