package com.estu.petify.petifycore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.Instant;
import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

}
