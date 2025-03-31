package com.rajatkumar.portfolio.portfolio_backend.rest.web.controller;

import com.rajatkumar.portfolio.portfolio_backend.AuthService;
import com.rajatkumar.portfolio.portfolio_backend.dto.JwtResponse;
import com.rajatkumar.portfolio.portfolio_backend.dto.LoginRequest;
import com.rajatkumar.portfolio.portfolio_backend.dto.RefreshTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Operations related to projects")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signup")
  public ResponseEntity<JwtResponse> register(@RequestBody LoginRequest registerUserDto) {
    return ResponseEntity.ok(authService.signup(registerUserDto));
  }

  @PostMapping("/login")
  @Operation(summary = "login user")
  public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authService.login(loginRequest));
  }

  @PostMapping("/refresh")
  @Operation(summary = "refresh token")
  public ResponseEntity<JwtResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
  }
}
