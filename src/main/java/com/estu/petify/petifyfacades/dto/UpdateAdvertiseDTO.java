package com.estu.petify.petifyfacades.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UpdateAdvertiseDTO implements Serializable {

    private String id;

    @NotBlank(message = "Title is mandatory.")
    private String title;

    @NotBlank(message = "Description is mandatory.")
    private String description;

    @NotBlank(message = "Price per hour is mandatory.")
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
