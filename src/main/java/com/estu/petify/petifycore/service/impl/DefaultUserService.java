package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.events.UserRegisterMailEvent;
import com.estu.petify.petifycore.model.VerificationTokenModel;
import com.estu.petify.petifycore.repository.VerificationTokenRepository;
import com.estu.petify.petifycore.service.PetifyAuthService;
import com.estu.petify.petifycore.service.PetifyMailService;
import com.estu.petify.petifyfacades.dto.UserDTO;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifystorefront.utils.PetifyDateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {

    private static final String DD_MM_YYYY = "dd/MM/yyyy HH:mm:ss";
    private static final String ACCOUNT_ACTIVATION_REQUEST_HEADER = "http://localhost:8080/api/v1/register/account-verification/?verificationToken=";

    private final UserRepository userRepository;
    private final PasswordEncoder petifyPasswordEncoder;
    private final PetifyAuthService petifyAuthService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PetifyMailService petifyMailService;

    @Override
    public List<UserModel> getUsers(){

        return userRepository.findAll();
    }

    @Override
    public UserModel register(final UserDTO newUser){
        final UserModel userModel = new UserModel();
        try{
            userModel.setUsername(newUser.getEMail());
            userModel.setPassword(petifyPasswordEncoder.encode(newUser.getPassword()));
            userModel.setFirstName(newUser.getFirstName());
            userModel.setLastName(newUser.getLastName());
            userModel.setPhoneNumber(newUser.getPhoneNumber());
            userModel.setBirthDate(newUser.getBirthDate());
            userModel.setEMail(newUser.getEMail());
            userModel.setGender(newUser.getGender());
            userModel.setAddress(newUser.getAddress());
            userModel.setCreationTime(PetifyDateUtils.getCurrentDateWithPattern(DD_MM_YYYY));
            userModel.setImage(newUser.getImage());
            userModel.setActivated(Boolean.FALSE);

            generateAndSendUserRegistrationMail(userModel);

            userRepository.save(userModel);

        } catch (Exception e){
            log.error("ERR: Unable to create new user, [ {} ]", newUser.getEMail());
        }

        return userModel;
    }

    @Override
    public UserModel updateProfile(final UserDTO userDTO){

        final UserModel userModel = petifyAuthService.getCurrentUser();

        try{
            userModel.setUsername(userDTO.getEMail());
            userModel.setFirstName(userDTO.getFirstName());
            userModel.setLastName(userDTO.getLastName());
            userModel.setPhoneNumber(userDTO.getPhoneNumber());
            userModel.setBirthDate(userDTO.getBirthDate());
            userModel.setEMail(userDTO.getEMail());
            userModel.setGender(userDTO.getGender());
            userModel.setAddress(userDTO.getAddress());
            userModel.setImage(userDTO.getImage());
            userRepository.save(userModel);
        }
        catch (Exception e){
            log.error("ERR: Unable to update user informations, [ {} ]", userDTO.getEMail());
        }

        return userModel;
    }

    @Override
    public void deleteUserById(final String id){

        try{
            verificationTokenRepository.deleteVerificationTokenModelByUserId(id);
            userRepository.deleteUserModelById(id);
        }
        catch (Exception e){
            log.error("ERR: Unable to delete user !");
        }
    }

    @Override
    public List<UserModel> getUserByEmail(final String email) {

        return userRepository.findUserModelByEMail(email);
    }

    private void generateAndSendUserRegistrationMail(final UserModel userModel){
        final String verificationToken = generateVerificationToken(userModel);

        UserRegisterMailEvent userRegisterMailEvent = new UserRegisterMailEvent();
        userRegisterMailEvent.setSubject("Please Activate Your Petify Account");
        userRegisterMailEvent.setUser(userModel.getEMail());
        userRegisterMailEvent.setMessage("Thank You for sign-in up to Petify, "
                + "Please click on the below link to activate your account: "
                + ACCOUNT_ACTIVATION_REQUEST_HEADER
                + verificationToken);

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
