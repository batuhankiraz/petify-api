package com.estu.petify.petifycore.model;

import com.estu.petify.petifycore.model.user.UserModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "medias")
public class MediaModel implements Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    /**
     * image bytes can have large lengths so we specify a value
     * which is more than the default length for picByte column
     */
    @Column(name = "picByte")
    @Lob
    private byte[] picByte;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UserModel user;

    public MediaModel() {
        // Empty Constructor.
    }

    public MediaModel(String name, String type, byte[] picByte) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "MediaModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", picByte=" + Arrays.toString(picByte) +
                ", user=" + user +
                '}';
    }
}
