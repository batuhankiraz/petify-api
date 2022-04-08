package com.estu.petify.petifyfacades.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
public class AdvertiseDTO implements Serializable {

    private String id;

    private String token;

    @NotBlank(message = "Title is mandatory.")
    private String title;

    @NotBlank(message = "Description is mandatory.")
    private String description;

    @NotBlank(message = "Price per hour is mandatory.")
    @Pattern(regexp = "(0|[1-9]\\d*)?(\\.\\d+)?(?<=\\d)$", message = "Please enter a valid price value. Exp: 0.5, 1, 1.0, 1.5")
    private String price;

    @NotBlank(message = "Country is mandatory.")
    private String country;

    @NotBlank(message = "City is mandatory.")
    private String city;

    @NotBlank(message = "Town is mandatory.")
    private String town;

    @NotBlank(message = "Pet Preferences is mandatory.")
    private String petPreferences;

}
