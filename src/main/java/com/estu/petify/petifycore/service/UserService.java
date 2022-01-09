package com.estu.petify.petifycore.service;

import com.estu.petify.petifyfacades.dto.UserDTO;
import com.estu.petify.petifycore.model.user.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserModel> getUsers();
    UserModel register(final UserDTO newUser);
    UserModel updateProfile(final String username, final UserDTO userDTO);
    void deleteUserById(final String id);
    UserModel getUserByUsername(final String username);
}
