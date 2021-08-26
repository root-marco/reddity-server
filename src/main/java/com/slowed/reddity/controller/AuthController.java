package com.slowed.reddity.controller;

import com.slowed.reddity.dto.RegisterRequest;
import com.slowed.reddity.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {

    authService.signup(registerRequest);
    return new ResponseEntity<>("user registration successful", HttpStatus.OK);

  }

  @GetMapping("accountVerification/{token}")
  public ResponseEntity<String> verifyAccount(@PathVariable String token) {

    authService.verifyAccount(token);
    return new ResponseEntity<>("account activated successfully", HttpStatus.OK);

  }

}
