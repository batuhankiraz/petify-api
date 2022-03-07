package com.estu.petify.petifycore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

}
