//package com.rajatkumar.portfolio.portfolio_backend.Configuration;
//
//import com.rajatkumar.portfolio.portfolio_backend.api.UserRepository;
//import com.rajatkumar.portfolio.portfolio_backend.dao.User;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AdminUserInitializer {
//  @Autowired
//  private UserRepository userRepository;
//
//  @Autowired
//  private PasswordEncoder passwordEncoder;
//
//  @PostConstruct
//  public void initAdminUser() {
//    if (userRepository.count() < 3) {
//      User adminUser = new User();
//      adminUser.setUsername("admin");
//      adminUser.setPassword(passwordEncoder.encode("admin123")); // Use a secure password
//      adminUser.setRole("ADMIN");
//      userRepository.save(adminUser);
//
//      System.out.println("Admin user created successfully");
//    }
//  }
//}
