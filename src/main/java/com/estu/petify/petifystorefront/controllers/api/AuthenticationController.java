package com.estu.petify.petifystorefront.controllers.api;

import com.estu.petify.petifycore.exceptions.PetifyJwtException;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifyfacades.dto.LoginDTO;
import com.estu.petify.petifyfacades.dto.RefreshTokenDTO;
import com.estu.petify.petifyfacades.dto.response.AuthenticationResponse;
import com.estu.petify.petifystorefront.controllers.CustomAbstractController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Slf4j
public class AuthenticationController extends CustomAbstractController {

    private final PetifyAuthService petifyAuthService;

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody LoginDTO loginDTO) throws PetifyJwtException {

        return petifyAuthService.login(loginDTO);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {

        return petifyAuthService.refreshToken(refreshTokenDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody final RefreshTokenDTO refreshTokenDTO) {

        petifyAuthService.logout(refreshTokenDTO.getRefreshToken());

        return new ResponseEntity<>("Refresh Token deleted. || Logged Out Successfully.", HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserModel> getCurrentUserByJwtToken(@RequestParam final String refreshToken){

        final UserModel currentUserByRefreshToken = StringUtils.hasText(refreshToken) ? petifyAuthService.getCurrentUserByRefreshToken(refreshToken) : null;

        return Objects.nonNull(currentUserByRefreshToken) ? new ResponseEntity<>(currentUserByRefreshToken, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
