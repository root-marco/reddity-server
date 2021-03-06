package com.slowed.reddity.service;

import com.slowed.reddity.dto.AuthenticationResponse;
import com.slowed.reddity.dto.LoginRequest;
import com.slowed.reddity.dto.RefreshTokenRequest;
import com.slowed.reddity.dto.RegisterRequest;
import com.slowed.reddity.exceptions.SpringReddityException;
import com.slowed.reddity.entity.NotificationEmail;
import com.slowed.reddity.entity.User;
import com.slowed.reddity.entity.VerificationToken;
import com.slowed.reddity.repository.UserRepository;
import com.slowed.reddity.repository.VerificationTokenRepository;
import com.slowed.reddity.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

  private final VerificationTokenRepository verificationTokenRepository;
  private final RefreshTokenService refreshTokenService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final MailService mailService;

  public void signup(RegisterRequest registerRequest) {

    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setCreated(Instant.now());
    user.setEnabled(false);
    userRepository.save(user);
    String token = generateVerificationToken(user);

    mailService.sendMail(
        new NotificationEmail(
        "please activate your account", user.getEmail(),
        "thank you for signing up to Reddity, " +
        "please click on the below url to activate your account : " +
        "http://localhost:8080/api/auth/accountVerification/" + token
      )
    );

  }

  @Transactional(readOnly = true)
  public User getCurrentUser() {

    org.springframework.security.core.userdetails.User principal =
      (org.springframework.security.core.userdetails.User) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();

    return userRepository.findByUsername(principal.getUsername())
      .orElseThrow(() -> new UsernameNotFoundException("username not found - " + principal.getUsername()));

  }

  private String generateVerificationToken(User user) {

    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(user);
    verificationTokenRepository.save(verificationToken);
    return token;

  }

  public AuthenticationResponse login(LoginRequest loginRequest) {

    Authentication authenticate = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authenticate);

    String authenticationToken = jwtProvider.generateToken(authenticate);

    return AuthenticationResponse.builder()
      .authenticationToken(authenticationToken)
      .refreshToken(refreshTokenService.generateRefreshToken().getToken())
      .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
      .username(loginRequest.getUsername())
      .build();

  }

  public void verifyAccount(String token) {

    Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
    verificationToken.orElseThrow(() ->
      new SpringReddityException("invalid token"));
    fetchUserAndEnable(verificationToken.get());

  }

  private void fetchUserAndEnable(VerificationToken verificationToken) {

    String username = verificationToken.getUser().getUsername();
    User user = userRepository.findByUsername(username).orElseThrow(() ->
      new SpringReddityException("user not found: " + username));
    user.setEnabled(true);
    userRepository.save(user);

  }

  public boolean isLoggedIn() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();

  }

  public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

    refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

    String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());

    return AuthenticationResponse.builder()
      .authenticationToken(token)
      .refreshToken(refreshTokenRequest.getRefreshToken())
      .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
      .username(refreshTokenRequest.getUsername())
      .build();

  }

}
