package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dto.JwtResponse;
import com.rajatkumar.portfolio.portfolio_backend.dto.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

  JwtResponse signup(LoginRequest input);

  JwtResponse login(LoginRequest request);

  JwtResponse refreshToken(String refreshToken);
}
