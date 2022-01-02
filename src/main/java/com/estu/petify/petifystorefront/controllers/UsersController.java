package com.estu.petify.petifystorefront.controllers;

import com.estu.petify.petifycore.model.user.UserModel;
import com.estu.petify.petifycore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserService defaultUserService;

    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getUsers(){
        try{
            List<UserModel> userModels = defaultUserService.getUsers();
            if (CollectionUtils.isEmpty(userModels)){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userModels, HttpStatus.OK);

        } catch (Exception exception){
            LOGGER.error("PTFY_ERR: Unexpected error occured while trying to Getting All Users from database.", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
