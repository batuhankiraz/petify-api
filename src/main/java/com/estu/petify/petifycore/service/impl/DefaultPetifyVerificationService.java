package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.model.VerificationTokenModel;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.repository.VerificationTokenRepository;
import com.estu.petify.petifycore.service.PetifyVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service("petifyVerificationService")
@RequiredArgsConstructor
@Slf4j
public class DefaultPetifyVerificationService implements PetifyVerificationService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public Boolean verifyAccount(final String token) throws Exception {

        final Optional<VerificationTokenModel> verificationTokenModel = Optional.ofNullable(verificationTokenRepository.findByToken(token)
                                                                        .orElseThrow(() -> new Exception("Invalid Verification Token for Account Activation.")));

        if (verificationTokenModel.isPresent()){
            try {

                return fetchUserAndActivateAccount(verificationTokenModel.get());
            }
            catch (final Exception e){
                log.error("ERR: Couldn't fetch User and Verification Token for account activation. || Cause: {}", e.getCause().getMessage());
            }
        }
        return Boolean.FALSE;
    }

    private Boolean fetchUserAndActivateAccount(final VerificationTokenModel verificationTokenModel) throws Exception {

        final String username = verificationTokenModel.getUsername();

        final UserModel userModel = userRepository.findByUsername(username).orElseThrow(() -> new Exception("User doesn't exist in database for Account Activation."));
        if (Objects.nonNull(userModel)){
            userModel.setActivated(Boolean.TRUE);

            userRepository.save(userModel);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
