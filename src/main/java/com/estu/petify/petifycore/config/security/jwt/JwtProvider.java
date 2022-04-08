package com.estu.petify.petifycore.config.security.jwt;

import com.estu.petify.petifycore.exceptions.PetifyJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${petify.jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() throws PetifyJwtException {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (final KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new PetifyJwtException("Exception occurred while loading keystore", e);
        }
    }

    public String generateJwtToken(final SecurityContext securityContext) throws PetifyJwtException {
        User principal = (User) securityContext.getAuthentication().getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public Boolean validateJWTToken(final String token) throws PetifyJwtException, KeyStoreException {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(token);
        return Boolean.TRUE;
    }

    private PrivateKey getPrivateKey() throws PetifyJwtException {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new PetifyJwtException("Exception occured while retrieving public key from keystore", e);
        }
    }

    private PublicKey getPublicKey() throws KeyStoreException {
        return keyStore.getCertificate("springblog").getPublicKey();
    }

    public String findUsernameFromJWT(final String token) throws KeyStoreException, PetifyJwtException {
        Claims claims = parser().setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String generateJwtTokenWithUsername(final String username) throws PetifyJwtException {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

    public void setJwtExpirationInMillis(Long jwtExpirationInMillis) {
        this.jwtExpirationInMillis = jwtExpirationInMillis;
    }

}
