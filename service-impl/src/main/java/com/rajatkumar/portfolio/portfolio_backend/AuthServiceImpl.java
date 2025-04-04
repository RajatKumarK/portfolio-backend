package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.Configuration.exception.InvalidTokenException;
import com.rajatkumar.portfolio.portfolio_backend.Configuration.utility.JwtUtil;
import com.rajatkumar.portfolio.portfolio_backend.dao.User;
import com.rajatkumar.portfolio.portfolio_backend.dao.api.UserRepository;
import com.rajatkumar.portfolio.portfolio_backend.dto.JwtResponse;
import com.rajatkumar.portfolio.portfolio_backend.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements
    AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;

  public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public JwtResponse signup(LoginRequest input) {
    User user = User.builder().
        username(input.getUsername()).
        password(passwordEncoder.encode(input.getPassword())).
        role("USER").
        build();
    userRepository.save(user);
    return login(input);
  }

  @Override
  public JwtResponse login(LoginRequest request) {
    // Authenticate with Spring Security
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Get user details after authentication
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Generate tokens
    String accessToken = jwtUtil.generateToken(userDetails);
    String refreshToken = "864000000";
    jwtUtil.generateRefreshToken(userDetails);

    return new JwtResponse(
        accessToken,
        refreshToken,
        user.getUsername(),
        user.getRole()
    );
  }

  @Override
  public JwtResponse refreshToken(String refreshToken) {
    // Validate refresh token
    String username = jwtUtil.extractUsername(refreshToken);

    if (username == null) {
      throw new InvalidTokenException("Invalid refresh token");
    }

    UserDetails userDetails = userRepository.findByUsername(username)
        .map(user -> org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getRole())
            .build())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    if (!jwtUtil.isTokenValid(refreshToken, userDetails)) {
      throw new InvalidTokenException("Refresh token expired");
    }

    // Generate new tokens
    String newAccessToken = jwtUtil.generateToken(userDetails);
    String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new JwtResponse(
        newAccessToken,
        newRefreshToken,
        user.getUsername(),
        user.getRole()
    );
  }
}
