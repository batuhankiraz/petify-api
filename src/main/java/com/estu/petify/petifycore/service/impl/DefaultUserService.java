package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.events.UserRegisterMailEvent;
import com.estu.petify.petifycore.model.VerificationTokenModel;
import com.estu.petify.petifycore.repository.VerificationTokenRepository;
import com.estu.petify.petifycore.service.PetifyMailService;
import com.estu.petify.petifyfacades.dto.RegisterDTO;
import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifyfacades.dto.UpdateProfileDTO;
import com.estu.petify.petifystorefront.utils.PetifyDateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {

    private static final String DD_MM_YYYY = "dd/MM/yyyy HH:mm";
    private static final String ACCOUNT_ACTIVATION_REQUEST_HEADER = "http://localhost:8080/api/v1/register/account-verification/?verificationToken=";

    private final UserRepository userRepository;
    private final PasswordEncoder petifyPasswordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PetifyMailService petifyMailService;

    @Override
    public List<UserModel> getUsers(){

        return userRepository.findAll();
    }

    @Override
    public UserModel register(final RegisterDTO newUser){
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
    public UserModel updateProfile(final UpdateProfileDTO updateProfileDTO){

        final String token = updateProfileDTO.getToken();
        final String email = updateProfileDTO.getEMail();
        final String firstName = updateProfileDTO.getFirstName();
        final String lastName = updateProfileDTO.getLastName();
        final String phoneNumber = updateProfileDTO.getPhoneNumber();
        final String birthDate = updateProfileDTO.getBirthDate();
        final String gender = updateProfileDTO.getGender();
        final String address = updateProfileDTO.getAddress();
        final String image = updateProfileDTO.getImage();

        final UserModel userByToken = StringUtils.hasText(token) ? userRepository.findByToken(token).orElseThrow() : null;

        try{
            if (Objects.nonNull(userByToken)){
                if (StringUtils.hasText(email)){
                    userByToken.setUsername(email);
                    userByToken.setEMail(email);
                }
                if (StringUtils.hasText(firstName)){
                    userByToken.setFirstName(firstName);
                }
                if (StringUtils.hasText(lastName)){
                    userByToken.setLastName(lastName);
                }
                if (StringUtils.hasText(phoneNumber)){
                    userByToken.setPhoneNumber(phoneNumber);
                }
                if (StringUtils.hasText(birthDate)){
                    userByToken.setBirthDate(birthDate);
                }
                if (StringUtils.hasText(gender)){
                    userByToken.setGender(gender);
                }
                if (StringUtils.hasText(address)){
                    userByToken.setAddress(address);
                }
                if (StringUtils.hasText(image)){
                    userByToken.setImage(image);
                }

                userRepository.save(userByToken);
            }
        }
        catch (Exception e){
            if (Objects.nonNull(userByToken) && StringUtils.hasText(token)){
                log.error("ERR: Unable to update {} information's with JWT Token: {}", userByToken.getUsername(), token);
            }
        }

        return userByToken;
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
