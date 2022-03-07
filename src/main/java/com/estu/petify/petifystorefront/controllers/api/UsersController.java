package com.estu.petify.petifystorefront.controllers.api;

import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifystorefront.controllers.CustomAbstractController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UsersController extends CustomAbstractController {

    private final UserService defaultUserService;

    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getUsers(){
        try{
            final List<UserModel> userModels = defaultUserService.getUsers();
            if (CollectionUtils.isEmpty(userModels)){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userModels, HttpStatus.OK);
        }
        catch (Exception exception){
            log.error("ERR: Unexpected error occurred while trying to getting all users. Cause: {}", exception.getCause().getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
