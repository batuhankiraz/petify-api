package com.estu.petify.petifystorefront.controllers.api;

import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.service.PetifyVerificationService;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifyfacades.dto.RegisterDTO;
import com.estu.petify.petifystorefront.controllers.CustomAbstractController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
@Slf4j
public class RegisterPageController extends CustomAbstractController {

    private final UserService defaultUserService;
    private final PetifyVerificationService petifyVerificationService;


    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<UserModel> register(@Valid @RequestBody final RegisterDTO registerDTO){
        UserModel newUser = defaultUserService.register(registerDTO);
        log.info("INFO: Successfully created Petify user for {}.", newUser.getUsername());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/account-verification/")
    public ResponseEntity<String> verifyAccount(@RequestParam final String verificationToken) throws Exception {
        petifyVerificationService.verifyAccount(verificationToken);
        log.info("INFO: Account activated successfully for [ verification_token: {} ]", verificationToken);
        return new ResponseEntity<>("Account activated successfully.", HttpStatus.OK);
    }

}
