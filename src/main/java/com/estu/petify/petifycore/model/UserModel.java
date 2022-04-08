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
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
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

    @Column(name = "image")
    private String image;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "token")
    private String token;
}
