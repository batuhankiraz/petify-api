package com.estu.petify.petifycore.model.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "refresh_token")
public class RefreshTokenModel {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "token")
    private String token;

    @Column(name = "createdDate")
    private Instant createdDate;

    public RefreshTokenModel() {
    }

    public RefreshTokenModel(String token, Instant createdDate) {
        this.token = token;
        this.createdDate = createdDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

}
