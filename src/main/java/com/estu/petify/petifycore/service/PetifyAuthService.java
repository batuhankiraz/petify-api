package com.estu.petify.petifycore.service;

import com.estu.petify.petifycore.exceptions.PetifyJwtException;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifyfacades.dto.LoginDTO;
import com.estu.petify.petifyfacades.dto.RefreshTokenDTO;
import com.estu.petify.petifyfacades.dto.response.AuthenticationResponse;

public interface PetifyAuthService {

    AuthenticationResponse login(final LoginDTO loginDTO) throws PetifyJwtException;

    UserModel getCurrentUser();

    UserModel getCurrentUserByRefreshToken(final String refreshToken);

    AuthenticationResponse refreshToken(final RefreshTokenDTO refreshTokenDTO);

    void logout(final String refreshToken);

}
