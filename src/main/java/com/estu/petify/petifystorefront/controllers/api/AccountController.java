package com.estu.petify.petifystorefront.controllers.api;

import com.estu.petify.petifycore.model.AdvertiseModel;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.service.PetifyAdvertiseService;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifyfacades.dto.UpdateProfileDTO;
import com.estu.petify.petifystorefront.controllers.CustomAbstractController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/my-account")
@RequiredArgsConstructor
@Slf4j
public class AccountController extends CustomAbstractController {

    private final UserService defaultUserService;
    private final PetifyAuthService petifyAuthService;
    private final PetifyAdvertiseService petifyAdvertiseService;

    @PostMapping(value = "/update-profile")
    public ResponseEntity<UserModel> updateMyInformation(@RequestBody UpdateProfileDTO updateProfileDTO) {
        final UserModel userModel = defaultUserService.updateProfile(updateProfileDTO);

        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }

    @GetMapping("/my-adverts")
    public ResponseEntity<List<AdvertiseModel>> getAdverts() {
        final String username = petifyAuthService.getCurrentUser().getUsername();
        final List<AdvertiseModel> adverts = petifyAdvertiseService.getUserAdvertsByUsername(username);

        return new ResponseEntity<>(adverts, HttpStatus.OK);
    }

    @DeleteMapping("/remove-account")
    public void deleteAccount(@RequestParam final String username) {
        try {
            defaultUserService.deleteByUsername(username);
        } catch (final Exception e) {
            log.error("ERR: While removing {}'s account. [Cause: {}]", username, e.getCause().getMessage());
        }
    }

}
