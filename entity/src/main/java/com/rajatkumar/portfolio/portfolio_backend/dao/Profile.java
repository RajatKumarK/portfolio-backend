package com.rajatkumar.portfolio.portfolio_backend.dao;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.GeneratedValue;
import java.util.Map;

@Entity
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String title;
  private String email;
  private String phone;
  private String location;
  private String avatarUrl;

  @Lob
  private String bio;

  private String linkedinUrl;
  private String githubUrl;
  private String twitterUrl;
  private String youtubeUrl;

  @ElementCollection
  private Map<String, String> skills; // skill name -> proficiency level

  // Getters, setters, constructors
}
