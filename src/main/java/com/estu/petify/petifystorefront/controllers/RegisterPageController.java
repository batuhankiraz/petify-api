package com.estu.petify.petifystorefront.controllers;

import com.estu.petify.petifycore.model.user.UserModel;
import com.estu.petify.petifycore.service.PetifyVerificationService;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifyfacades.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/v1/register")
public class RegisterPageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterPageController.class);

    @Autowired
    private UserService defaultUserService;
    @Autowired
    private PetifyVerificationService petifyVerificationService;


    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<UserModel> register(@Valid @RequestBody final UserDTO userDTO){
        UserModel newUser = defaultUserService.register(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/account-verification/")
    public ResponseEntity<String> verifyAccount(@RequestParam final String verificationToken) throws Exception {
        petifyVerificationService.verifyAccount(verificationToken);
        return new ResponseEntity<>("Account activated successfully.", HttpStatus.OK);
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
