package com.estu.petify.petifycore.model.advertising;

import com.estu.petify.petifycore.model.user.UserModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "advertise")
public class AdvertiseModel {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "advertiserName")
    private String advertiserName;

    @Column(name = "advertiserMail")
    private String advertiserMail;

    @Column(name = "advertiserPhoneNumber")
    private String advertiserPhoneNumber;

    @Column(name = "creationTime")
    private String creationTime;

    @Lob
    @Column(name = "advertiseDesc")
    private String description;

    @Column(name = "petName")
    private String petName;

    @Column(name = "petFamily")
    private String petFamily;

    @Column(name = "petGender")
    private String petGender;

    @Column(name = "petColor")
    private String petColor;

    @Lob
    @Column(name = "petDesc")
    private String petDesc;

    public AdvertiseModel() {
        // Empty Constructor.
    }

    public AdvertiseModel(String title, String type, String advertiserName, String advertiserMail, String advertiserPhoneNumber, String creationTime, String description, String petName, String petFamily, String petGender, String petColor, String petDesc) {
        this.title = title;
        this.type = type;
        this.advertiserName = advertiserName;
        this.advertiserMail = advertiserMail;
        this.advertiserPhoneNumber = advertiserPhoneNumber;
        this.creationTime = creationTime;
        this.description = description;
        this.petName = petName;
        this.petFamily = petFamily;
        this.petGender = petGender;
        this.petColor = petColor;
        this.petDesc = petDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getAdvertiserName() {
        return advertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        this.advertiserName = advertiserName;
    }

    public String getAdvertiserMail() {
        return advertiserMail;
    }

    public void setAdvertiserMail(String advertiserMail) {
        this.advertiserMail = advertiserMail;
    }

    public String getAdvertiserPhoneNumber() {
        return advertiserPhoneNumber;
    }

    public void setAdvertiserPhoneNumber(String advertiserPhoneNumber) {
        this.advertiserPhoneNumber = advertiserPhoneNumber;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
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

    @Override
    public String toString() {
        return "Advertise{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", advertiserName='" + advertiserName + '\'' +
                ", advertiserMail='" + advertiserMail + '\'' +
                ", advertiserPhoneNumber='" + advertiserPhoneNumber + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", description='" + description + '\'' +
                ", petName='" + petName + '\'' +
                ", petFamily='" + petFamily + '\'' +
                ", petGender='" + petGender + '\'' +
                ", petColor='" + petColor + '\'' +
                ", petDesc='" + petDesc + '\'' +
                '}';
    }

}
