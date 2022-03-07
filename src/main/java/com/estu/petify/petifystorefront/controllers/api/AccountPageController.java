package com.estu.petify.petifystorefront.controllers.api;

import com.estu.petify.petifycore.model.AdvertiseModel;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.service.PetifyAdvertiseService;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifyfacades.dto.UserDTO;
import com.estu.petify.petifystorefront.controllers.CustomAbstractController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/my-account")
@AllArgsConstructor
@Slf4j
public class AccountPageController extends CustomAbstractController {

    private final UserService defaultUserService;
    private final PetifyAuthService petifyAuthService;
    private final PetifyAdvertiseService petifyAdvertiseService;

    @GetMapping()
    public ResponseEntity<UserModel> myAccount(){
        try{
            UserModel currentUser = petifyAuthService.getCurrentUser();
            if (Objects.isNull(currentUser)){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }
        catch (Exception exception){
            log.error("ERR: Unexpected error occurred while trying to getting currently logged-in user's information s from database. Cause: {}", exception.getCause().getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update-profile", consumes = {"application/json"})
    public ResponseEntity<UserModel> updateMyInformation(@Valid @RequestBody UserDTO userDTO){
        final UserModel userModel = defaultUserService.updateProfile(userDTO);
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }

    @GetMapping("/my-adverts")
    public ResponseEntity<List<AdvertiseModel>> getAdverts(){

        final String username = petifyAuthService.getCurrentUser().getUsername();
        final List<AdvertiseModel> adverts = petifyAdvertiseService.getCurrentUserAdverts(username);
        if (CollectionUtils.isEmpty(adverts)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(adverts, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public void deleteAccount(){

        final UserModel currentUser = petifyAuthService.getCurrentUser();
        try {
            defaultUserService.deleteUserById(currentUser.getId());
        }
        catch (Exception e){
            log.error("ERR: Cause: {}" + e.getCause().getMessage());
        }
    }

}
