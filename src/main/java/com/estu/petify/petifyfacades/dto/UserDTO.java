package com.estu.petify.petifyfacades.dto;

import javax.validation.constraints.NotBlank;

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
    private String eMail;

    @NotBlank(message = "Gender is mandatory.")
    private String gender;

    private String address;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
