package com.estu.petify.petifycore.service;

public interface PetifyVerificationService {
    void verifyAccount(final String token) throws Exception;
}
