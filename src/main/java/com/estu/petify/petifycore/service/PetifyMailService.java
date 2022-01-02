package com.estu.petify.petifycore.service;

import com.estu.petify.petifycore.events.UserRegisterMailEvent;

public interface PetifyMailService {
    void sendUserRegisterMail(UserRegisterMailEvent userRegisterMailEvent);
}
