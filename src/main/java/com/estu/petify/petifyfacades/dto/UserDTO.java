package com.estu.petify.petifyfacades.dto;

import com.estu.petify.petifyfacades.annotations.email.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "Password is mandatory.")
    private String password;

    @NotBlank(message = "First Name is mandatory.")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory.")
    private String lastName;

    private String birthDate;

    @NotBlank(message = "Phone Number is mandatory.")
    private String phoneNumber;

    @NotBlank(message = "E-Mail is mandatory.")
    @UniqueEmail(message = "Email will be unique.")
    private String eMail;

    private String gender;

    private String address;

    private String image;

}
