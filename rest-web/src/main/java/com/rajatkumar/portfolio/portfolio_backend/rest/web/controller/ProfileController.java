package com.rajatkumar.portfolio.portfolio_backend.rest.web.controller;

import com.rajatkumar.portfolio.portfolio_backend.ProfileService;
import com.rajatkumar.portfolio.portfolio_backend.dao.Profile;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping
  public Profile getProfile() {
    return profileService.getProfile();
  }

  @PutMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Profile updateProfile(@RequestBody Profile profile) {
    return profileService.updateProfile(profile);
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
