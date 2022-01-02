package com.estu.petify.petifycore.model.user;

import com.estu.petify.petifycore.model.MediaModel;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserModel {

    // TODO-1: Validationlar ve Mesajlar.

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "birthDate")
    private String birthDate;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "eMail")
    private String eMail;

    @Column(name = "gender")
    private String gender;

    @Column(name = "creationTime")
    private String creationTime;

    @Column(name = "address")
    @Lob
    private String address;

    @Column(name = "activated")
    private Boolean activated;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MediaModel media;

    public UserModel() {
        // Empty Constructor.
    }

    public UserModel(String firstName, String lastName, String birthDate, String phoneNumber, String eMail, String gender, String creationTime, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.gender = gender;
        this.creationTime = creationTime;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public MediaModel getMedia() {
        return media;
    }

    public void setMedia(MediaModel media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", eMail='" + eMail + '\'' +
                ", gender='" + gender + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", address='" + address + '\'' +
                ", activated=" + activated +
                ", media=" + media +
                '}';
    }
}
