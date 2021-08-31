package com.slowed.reddity.security;

import com.slowed.reddity.exceptions.SpringReddityException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
public class JwtProvider {

  private KeyStore keyStore;

  @PostConstruct
  public void init() {

    try {
      keyStore = KeyStore.getInstance("JKS");
      InputStream resourceAsStream = getClass().getResourceAsStream("/reddity.jks");
      keyStore.load(resourceAsStream, "secret".toCharArray());
    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
      throw new SpringReddityException("exception occurred while loading keystore", e);
    }

  }

  public String generateToken(Authentication authentication) {

    org.springframework.security.core.userdetails.User principal =
      (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

    return Jwts.builder()
      .setSubject(principal.getUsername())
      .signWith(getPrivateKey())
      .compact();

  }

  private PrivateKey getPrivateKey() {

    try {
      return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
      throw new SpringReddityException("exception occurred while retrieving public key from keystore", e);
    }

  }

  public boolean validateToken(String jwt) {

    parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);

    return true;

  }

  private PublicKey getPublicKey() {

    try {
      return keyStore.getCertificate("springblog").getPublicKey();
    } catch (KeyStoreException e) {
      throw new SpringReddityException("exception occurred while retrieving public key from keystore", e);
    }

  }

  public String getUsernameFromJwt(String token) {

    Claims claims = parserBuilder()
      .setSigningKey(getPublicKey())
      .build()
      .parseClaimsJws(token)
      .getBody();

    return claims.getSubject();

  }

}
