package com.rajatkumar.portfolio.portfolio_backend.rest.web.controller;

import com.rajatkumar.portfolio.portfolio_backend.ProfileService;
import com.rajatkumar.portfolio.portfolio_backend.dao.Profile;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  @Autowired
  private ProfileService profileService;

//*
//   * Get the profile information.
//   *
//   * @return The profile entity
//

  @GetMapping
  public ResponseEntity<Profile> getProfile() {
    return ResponseEntity.ok(profileService.getProfile());
  }

//*
//   * Update the profile information.
//   * Requires admin authorization.
//   *
//   * @param profileDetails The updated profile data
//   * @return The updated profile


  @PutMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Profile> updateProfile(@Valid @RequestBody Profile profileDetails) {
    return ResponseEntity.ok(profileService.updateProfile(profileDetails));
  }

  @GetMapping("/skills")
  public Map<String, String> getSkills() {
    return profileService.getProfile().getSkills();
  }

  @PutMapping("/skills")
  @PreAuthorize("hasRole('ADMIN')")
  public Profile updateSkills(@RequestBody Map<String, String> skills) {
    return profileService.updateSkills(skills);
  }
}
