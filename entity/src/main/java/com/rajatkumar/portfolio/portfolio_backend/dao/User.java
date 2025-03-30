package com.rajatkumar.portfolio.portfolio_backend.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password; // Stored as BCrypt hash
  private String role; // "ADMIN"

  // Getters, setters, constructors
}
