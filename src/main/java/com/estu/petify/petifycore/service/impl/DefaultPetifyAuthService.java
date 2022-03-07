package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.config.security.jwt.JwtProvider;
import com.estu.petify.petifycore.exceptions.PetifyJwtException;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.PetifyRefreshTokenService;
import com.estu.petify.petifyfacades.dto.LoginDTO;
import com.estu.petify.petifyfacades.dto.RefreshTokenDTO;
import com.estu.petify.petifyfacades.dto.response.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.time.Instant;
import java.util.Objects;

@Service("petifyAuthService")
@AllArgsConstructor
@Transactional
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
        final UserModel user = Objects.nonNull(authentication) ? userRepository.findByUsername(loginDTO.getUsername()).orElseThrow() : null ;
        try{
            if (Objects.nonNull(user) && StringUtils.hasText(refreshToken)){
                user.setToken(refreshToken);
                userRepository.save(user);
            }
        } catch (Exception e){
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

        final var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

        return userRepository.findByUsername(username).orElseGet(null);
    }

    @Override
    public UserModel getCurrentUserByRefreshToken(final String refreshToken) {

        final UserModel userByToken = userRepository.findByToken(refreshToken).orElseThrow();

        return userByToken;
    }

    @Override
    public AuthenticationResponse refreshToken(final RefreshTokenDTO refreshTokenDTO){

        petifyRefreshTokenService.validateRefreshToken(refreshTokenDTO.getRefreshToken());
        String jwtToken = "";

        try {
            jwtToken = jwtProvider.generateJwtTokenWithUsername(refreshTokenDTO.getUsername());
        } catch (PetifyJwtException e) {
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
    public void logout(final String refreshToken) {

        final UserModel userByRefreshToken = userRepository.findByToken(refreshToken).orElseThrow();
        try {
            petifyRefreshTokenService.deleteRefreshToken(refreshToken);

            userByRefreshToken.setToken("");
            userRepository.save(userByRefreshToken);
        }
        catch (Exception e){
            log.warn("WARN: Couldn't remove token from user or refresh token in database. Refresh_Token: {}, Username: {}", refreshToken, userByRefreshToken.getUsername());
        }
    }

}
