package com.estu.petify.petifycore.service;

import com.estu.petify.petifyfacades.dto.UserDTO;
import com.estu.petify.petifycore.model.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> getUsers();
    UserModel register(final UserDTO newUser);
    UserModel updateProfile(final UserDTO userDTO);
    void deleteUserById(final String id);
    List<UserModel> getUserByEmail(final String email);
}
