package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.api.UserRepository;
import com.rajatkumar.portfolio.portfolio_backend.dao.User;
import com.rajatkumar.portfolio.portfolio_backend.dto.JwtResponse;
import com.rajatkumar.portfolio.portfolio_backend.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

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
    String refreshToken = jwtUtil.generateRefreshToken(userDetails);

    return new JwtResponse(
        accessToken,
        refreshToken,
        user.getUsername(),
        user.getRole()
    );
  }

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
