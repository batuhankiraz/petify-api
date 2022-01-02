package com.estu.petify.petifystorefront.controllers;

import com.estu.petify.petifycore.model.advertising.AdvertiseModel;
import com.estu.petify.petifycore.model.user.UserModel;
import com.estu.petify.petifycore.service.PetifyAdvertiseService;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifyfacades.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/my-account")
public class AccountPageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountPageController.class);

    @Autowired
    private UserService defaultUserService;
    @Autowired
    private PetifyAuthService petifyAuthService;
    @Autowired
    private PetifyAdvertiseService petifyAdvertiseService;

    @GetMapping()
    public ResponseEntity<UserModel> myAccount(){
        try{
            UserModel currentUser = petifyAuthService.getCurrentUser();
            if (Objects.isNull(currentUser)){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(currentUser, HttpStatus.OK);

        } catch (Exception exception){
            LOGGER.error("PTFY_ERR: Unexpected error occured while trying to getting currently logged-in user's informations from database.", exception);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update-profile", consumes = {"application/json"})
    public ResponseEntity<UserModel> updateMyInformation(@Valid @RequestBody UserDTO userDTO){
        //TODO: Validasyon uygulanacak -> eğer valid değil ise yeni bir response fırlat.
        UserModel userModel = defaultUserService.updateProfile(userDTO);
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }

    @GetMapping("/my-adverts")
    public ResponseEntity<List<AdvertiseModel>> getAdverts(){
        final String username = petifyAuthService.getCurrentUser().getUsername();
        List<AdvertiseModel> adverts = petifyAdvertiseService.getCurrentUserAdverts(username);
        if (CollectionUtils.isEmpty(adverts)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(adverts, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public void deleteAccount(){
        UserModel currentUser = petifyAuthService.getCurrentUser();
        try {
            defaultUserService.deleteUserById(currentUser.getId());
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage());
        }
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
