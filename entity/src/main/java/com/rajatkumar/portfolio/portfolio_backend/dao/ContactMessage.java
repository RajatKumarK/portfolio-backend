package com.rajatkumar.portfolio.portfolio_backend.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Entity
public class ContactMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String subject;

  @Lob
  private String message;

  private LocalDateTime receivedAt;
  private boolean read;

  // Getters, setters, constructors
}
