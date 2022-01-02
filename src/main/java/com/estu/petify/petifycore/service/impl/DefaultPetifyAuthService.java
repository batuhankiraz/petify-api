package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.config.security.jwt.JwtProvider;
import com.estu.petify.petifycore.exceptions.PetifyJwtException;
import com.estu.petify.petifycore.exceptions.PetifyUserNotFoundException;
import com.estu.petify.petifycore.model.user.UserModel;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.PetifyRefreshTokenService;
import com.estu.petify.petifyfacades.dto.LoginDTO;
import com.estu.petify.petifyfacades.dto.RefreshTokenDTO;
import com.estu.petify.petifyfacades.response.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service("petifyAuthService")
@AllArgsConstructor
@Transactional
public class DefaultPetifyAuthService implements PetifyAuthService {

    private final AuthenticationManager authenticationManagerBean;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PetifyRefreshTokenService petifyRefreshTokenService;

    @Override
    public AuthenticationResponse login(LoginDTO loginDTO) throws PetifyJwtException {
        Authentication authentication = authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        final String jwtToken = jwtProvider.generateJwtToken(securityContext);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(petifyRefreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginDTO.getUsername())
                .build();
    }

    @Override
    public UserModel getCurrentUser() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? principal.getUsername() : principal.toString();
        return userRepository.findByUsername(username).orElseThrow(() -> new PetifyUserNotFoundException("User with username= [" + username + "] was not found."));
    }

    @Override
    public AuthenticationResponse refreshToken(final RefreshTokenDTO refreshTokenDTO){
        petifyRefreshTokenService.validateRefreshToken(refreshTokenDTO.getRefreshToken());
        String jwtToken = null;
        try {
            jwtToken = jwtProvider.generateJwtTokenWithUsername(refreshTokenDTO.getUsername());
        } catch (PetifyJwtException e) {
            e.printStackTrace();
        }
        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshTokenDTO.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenDTO.getUsername())
                .build();
    }

    @Override
    public void logout() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        authentication.setAuthenticated(Boolean.FALSE);
    }

}
