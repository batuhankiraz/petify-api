package com.estu.petify.petifystorefront.controllers;

import com.estu.petify.petifycore.exceptions.PetifyJwtException;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.PetifyRefreshTokenService;
import com.estu.petify.petifyfacades.dto.LoginDTO;
import com.estu.petify.petifyfacades.dto.RefreshTokenDTO;
import com.estu.petify.petifyfacades.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private PetifyAuthService petifyAuthService;

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody LoginDTO loginDTO) throws PetifyJwtException {
        return petifyAuthService.login(loginDTO);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return petifyAuthService.refreshToken(refreshTokenDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        petifyAuthService.logout();
        return new ResponseEntity<>("Logged Out Successfully.", HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
