package com.estu.petify.petifycore.exceptions;

public class PetifyJwtException extends Throwable {

    public PetifyJwtException(String message, Exception exception) {
        super(message, exception);
    }

    public PetifyJwtException(String message) {
        super(message);
    }

}
