package com.estu.petify.petifyfacades.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertiseDTO implements Serializable {

    @NotBlank(message = "Advertise Title is mandatory.")
    private String title;

    @NotBlank(message = "Advertise type is mandatory.")
    private String type;

    @NotBlank(message = "Advertise description is mandatory.")
    private String description;

    private String petName;

    @NotBlank(message = "Family for Pet is mandatory.")
    private String petFamily;

    @NotBlank(message = "Gender for Pet is mandatory.")
    private String petGender;

    @NotBlank(message = "Color for Pet is mandatory.")
    private String petColor;

    @NotBlank(message = "Description for Pet is mandatory.")
    private String petDesc;

}
