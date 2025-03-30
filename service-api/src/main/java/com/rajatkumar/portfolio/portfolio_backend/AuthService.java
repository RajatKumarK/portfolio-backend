package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dto.JwtResponse;
import com.rajatkumar.portfolio.portfolio_backend.dto.LoginRequest;

public interface AuthService {

  public JwtResponse login(LoginRequest request);

  public JwtResponse refreshToken(String refreshToken);
}
