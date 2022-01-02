package com.estu.petify.petifycore.model.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "verification_token")
public class VerificationTokenModel {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "token")
    private String token;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private UserModel user;

    @Column(name = "expiryDate")
    private Instant expiryDate;

    public VerificationTokenModel() {
        // Empty Constructor.
    }

    public VerificationTokenModel(String token, UserModel user, Instant expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

}
