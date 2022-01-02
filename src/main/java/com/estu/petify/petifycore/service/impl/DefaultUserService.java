package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.events.UserRegisterMailEvent;
import com.estu.petify.petifycore.model.user.VerificationTokenModel;
import com.estu.petify.petifycore.repository.VerificationTokenRepository;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.PetifyMailService;
import com.estu.petify.petifyfacades.dto.UserDTO;
import com.estu.petify.petifycore.model.user.UserModel;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifystorefront.utils.PetifyDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class DefaultUserService implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserService.class);

    private static final String DD_MM_YYYY = "dd/MM/yyyy HH:mm:ss";
    private static final String ACCOUNT_ACTIVATION_REQUEST_HEADER = "http://localhost:8080/api/v1/register/account-verification/?verificationToken=";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder petifyPasswordEncoder;
    @Autowired
    private PetifyAuthService petifyAuthService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PetifyMailService petifyMailService;

    @Override
    public List<UserModel> getUsers(){
        return userRepository.findAll();
    }

    @Override
    public UserModel register(final UserDTO newUser){
        final UserModel userModel = new UserModel();
        try{
            userModel.setUsername(newUser.geteMail());
            userModel.setPassword(petifyPasswordEncoder.encode(newUser.getPassword()));
            userModel.setFirstName(newUser.getFirstName());
            userModel.setLastName(newUser.getLastName());
            userModel.setPhoneNumber(newUser.getPhoneNumber());
            userModel.setBirthDate(newUser.getBirthDate());
            userModel.seteMail(newUser.geteMail());
            userModel.setGender(newUser.getGender());
            userModel.setAddress(newUser.getAddress());
            userModel.setCreationTime(PetifyDateUtils.getCurrentDateWithPattern(DD_MM_YYYY));
            userModel.setActivated(Boolean.FALSE);

            generateAndSendUserRegistrationMail(userModel);
            userRepository.save(userModel);
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage());
        }
        return userModel;
    }

    @Override
    public UserModel updateProfile(final UserDTO userDTO){

        UserModel userModel = petifyAuthService.getCurrentUser();
        try{
            userModel.setUsername(userDTO.geteMail());
            userModel.setFirstName(userDTO.getFirstName());
            userModel.setLastName(userDTO.getLastName());
            userModel.setPhoneNumber(userDTO.getPhoneNumber());
            userModel.setBirthDate(userDTO.getBirthDate());
            userModel.seteMail(userDTO.geteMail());
            userModel.setGender(userDTO.getGender());
            userModel.setAddress(userDTO.getAddress());
            userRepository.save(userModel);
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage());
        }
        return userModel;
    }

    @Override
    public void deleteUserById(final String id){
        try{
            verificationTokenRepository.deleteVerificationTokenModelByUserId(id);
            userRepository.deleteUserModelById(id);
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    private void generateAndSendUserRegistrationMail(final UserModel userModel){
        final String verificationToken = generateVerificationToken(userModel);
        UserRegisterMailEvent userRegisterMailEvent = new UserRegisterMailEvent();
        userRegisterMailEvent.setSubject("Please Activate Your Petify Account");
        userRegisterMailEvent.setUser(userModel.geteMail());
        userRegisterMailEvent.setMessage("Thank You for signin up to Petify, " + "Please click on the below url to activate your account: " +
                ACCOUNT_ACTIVATION_REQUEST_HEADER + verificationToken);
        petifyMailService.sendUserRegisterMail(userRegisterMailEvent);
    }

    private String generateVerificationToken(final UserModel userModel){
        final String verificationToken = UUID.randomUUID().toString();
        VerificationTokenModel verificationTokenModel = new VerificationTokenModel();
        verificationTokenModel.setToken(verificationToken);
        verificationTokenModel.setUser(userModel);
        verificationTokenRepository.save(verificationTokenModel);
        return verificationToken;
    }

}
