package com.estu.petify.petifycore.service;

import com.estu.petify.petifycore.exceptions.PetifyJwtException;
import com.estu.petify.petifycore.model.user.UserModel;
import com.estu.petify.petifyfacades.dto.LoginDTO;
import com.estu.petify.petifyfacades.dto.RefreshTokenDTO;
import com.estu.petify.petifyfacades.response.AuthenticationResponse;

public interface PetifyAuthService {
    AuthenticationResponse login(final LoginDTO loginDTO) throws PetifyJwtException;
    UserModel getCurrentUser();
    AuthenticationResponse refreshToken(final RefreshTokenDTO refreshTokenDTO);
    void logout();
}
