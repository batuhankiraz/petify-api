package com.estu.petify.petifycore.exceptions;

public class PetifyUserNotFoundException extends RuntimeException{

    public PetifyUserNotFoundException(final String message){
        super(message);
    }
}
