package com.estu.petify.petifyfacades.dto;

import com.estu.petify.petifyfacades.annotations.email.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {


    @NotBlank(message = "First Name is mandatory.")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory.")
    private String lastName;

    @NotBlank(message = "Password is mandatory.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must contains at least 8 characters with uppercase, lowercase letters and numbers.")
    private String password;

    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$", message = "Birthdate must be dd/mm/yyyy format.")
    private String birthDate;

    @NotBlank(message = "Phone Number is mandatory.")
    private String phoneNumber;

    private String email;

    private String gender;

    private String address;

}
