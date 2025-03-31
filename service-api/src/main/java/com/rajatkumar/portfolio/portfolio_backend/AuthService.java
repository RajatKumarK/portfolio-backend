package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dto.JwtResponse;
import com.rajatkumar.portfolio.portfolio_backend.dto.LoginRequest;

public interface AuthService {

  JwtResponse login(LoginRequest request);

  JwtResponse refreshToken(String refreshToken);
}
