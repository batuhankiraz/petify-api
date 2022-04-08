package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.config.security.jwt.JwtProvider;
import com.estu.petify.petifycore.exceptions.PetifyJwtException;
import com.estu.petify.petifycore.exceptions.PetifyUserNotFoundException;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.PetifyRefreshTokenService;
import com.estu.petify.petifyfacades.dto.LoginDTO;
import com.estu.petify.petifyfacades.dto.RefreshTokenDTO;
import com.estu.petify.petifyfacades.dto.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Objects;

@Service("petifyAuthService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DefaultPetifyAuthService implements PetifyAuthService {

    private final AuthenticationManager authenticationManagerBean;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PetifyRefreshTokenService petifyRefreshTokenService;

    @Override
    public AuthenticationResponse login(LoginDTO loginDTO) throws PetifyJwtException {
        final Authentication authentication = authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        final SecurityContext securityContext = SecurityContextHolder.getContext();

        securityContext.setAuthentication(authentication);

        final String jwtToken = jwtProvider.generateJwtToken(securityContext);
        final String refreshToken = petifyRefreshTokenService.generateRefreshToken().getToken();

        final UserModel user = Objects.nonNull(authentication) ? userRepository.findByUsername(loginDTO.getUsername()).orElseThrow() : null;

        try {
            if (Objects.nonNull(user) && StringUtils.hasText(refreshToken)) {
                user.setToken(jwtToken);
                userRepository.save(user);
            }
        } catch (final Exception e) {
            log.warn("WARN: Couldn't update user: {} with refresh token: {}", user.getUsername(), refreshToken);
        }

        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshToken)
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginDTO.getUsername())
                .build();
    }

    @Override
    public UserModel getCurrentUser() {
        final User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String username = principal instanceof UserDetails ? principal.getUsername() : principal.toString();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new PetifyUserNotFoundException("User with username= [" + username + "] was not found."));
    }

    @Override
    public UserModel getCurrentUserByToken(final String jwtToken) {

        return userRepository.findByToken(jwtToken).orElseThrow();
    }

    @Override
    public AuthenticationResponse refreshToken(final RefreshTokenDTO refreshTokenDTO) {
        petifyRefreshTokenService.validateRefreshToken(refreshTokenDTO.getRefreshToken());
        String jwtToken = "";

        try {
            jwtToken = jwtProvider.generateJwtTokenWithUsername(refreshTokenDTO.getUsername());
        } catch (final PetifyJwtException e) {
            log.warn("WARN: Couldn't generate Jwt Token: {} for Username: {}", jwtToken, refreshTokenDTO.getUsername());
        }

        return AuthenticationResponse.builder()
                .authenticationToken(jwtToken)
                .refreshToken(refreshTokenDTO.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenDTO.getUsername())
                .build();
    }

    @Override
    public void logout(final RefreshTokenDTO refreshTokenDTO) {
        final String username = refreshTokenDTO.getUsername();
        final String refreshToken = refreshTokenDTO.getRefreshToken();
        final UserModel user = StringUtils.hasText(username) ? userRepository.findByUsername(username).orElseThrow() : null;

        try {
            petifyRefreshTokenService.deleteRefreshToken(refreshToken);

            if (Objects.nonNull(user)) {
                user.setToken("");
                userRepository.save(user);
            }
        } catch (final Exception e) {
            log.warn("ERR: Couldn't remove token from user or refresh token in database. Refresh Token: {}, Username: {}", refreshToken, username);
        }
    }

}

