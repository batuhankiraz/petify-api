package com.estu.petify.petifycore.service.impl;

import com.estu.petify.petifycore.model.user.UserModel;
import com.estu.petify.petifycore.model.user.VerificationTokenModel;
import com.estu.petify.petifycore.repository.UserRepository;
import com.estu.petify.petifycore.repository.VerificationTokenRepository;
import com.estu.petify.petifycore.service.PetifyVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("petifyVerificationService")
public class DefaultPetifyVerificationService implements PetifyVerificationService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void verifyAccount(final String token) throws Exception {
        Optional<VerificationTokenModel> verificationTokenModel = verificationTokenRepository.findByToken(token);
        verificationTokenModel.orElseThrow(() -> new Exception("Invalid Verification Token."));
        fetchUserAndActivateAccount(verificationTokenModel.get());
    }

    private void fetchUserAndActivateAccount(final VerificationTokenModel verificationTokenModel) throws Exception {
        final String username = verificationTokenModel.getUser().getUsername();
        final UserModel userModel = userRepository.findByUsername(username).orElseThrow(() -> new Exception("User doesn't exist."));
        userModel.setActivated(Boolean.TRUE);
        userRepository.save(userModel);
    }

}
