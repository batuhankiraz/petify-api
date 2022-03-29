package com.estu.petify.petifycore.service;

public interface PetifyVerificationService {

    Boolean verifyAccount(final String token) throws Exception;
}
