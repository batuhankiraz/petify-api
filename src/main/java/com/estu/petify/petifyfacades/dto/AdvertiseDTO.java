package com.estu.petify.petifyfacades.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetFamily() {
        return petFamily;
    }

    public void setPetFamily(String petFamily) {
        this.petFamily = petFamily;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public String getPetDesc() {
        return petDesc;
    }

    public void setPetDesc(String petDesc) {
        this.petDesc = petDesc;
    }

}
