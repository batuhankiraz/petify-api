package com.estu.petify.petifyfacades.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Username is mandatory.")
    private String username;
    @NotBlank(message = "Password is mandatory.")
    private String password;

}
