package com.estu.petify.petifycore.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "advertise")
public class AdvertiseModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "creationTime")
    private Instant creationTime;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "town")
    private String town;

    @Column(name = "address")
    private String address;

    @Column(name = "petPreferences")
    @ElementCollection
    private List<String> petPreferencesList;

}
