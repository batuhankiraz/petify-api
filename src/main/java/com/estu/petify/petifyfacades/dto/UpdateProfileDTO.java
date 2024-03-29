package com.estu.petify.petifyfacades.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateProfileDTO {

    private String password;

    private String firstName;

    private String lastName;

    private String birthDate;

    private String phoneNumber;

    private String eMail;

    private String gender;

    private String address;

    private String image;

    private String token;

}
