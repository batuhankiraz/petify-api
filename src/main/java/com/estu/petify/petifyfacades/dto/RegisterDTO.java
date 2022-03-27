package com.estu.petify.petifyfacades.dto;

import com.estu.petify.petifyfacades.annotations.email.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "Password is mandatory.")
    private String password;

    @NotBlank(message = "First Name is mandatory.")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory.")
    private String lastName;

    @Pattern(regexp = "([0-9]{2})\\\\([0-9]{2})\\\\([0-9]{4})", message = "Birthdate must be DD/MM/YYYY format.")
    private String birthDate;

    @NotBlank(message = "Phone Number is mandatory.")
    @Min(value = 11, message = "Phone Number must contain 11 character.")
    @Max(value = 11, message = "Phone Number must contain 11 character.")
    private String phoneNumber;

    @NotBlank(message = "Email is mandatory.")
    @UniqueEmail(message = "Email will be unique.")
    private String eMail;

    private String gender;

    private String address;

    private String image;

    private String token;

}
