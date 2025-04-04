package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.dao.api.ProfileRepository;
import com.rajatkumar.portfolio.portfolio_backend.dao.Profile;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

  @Autowired
  private ProfileRepository profileRepository;

  /**
   * Retrieves the profile information.
   *
   * @return The profile entity
   */
  public Profile getProfile() {
    Profile profile = profileRepository.findFirstByOrderById();
    if (profile == null) {
      // Initialize with default profile if none exists
      profile = createDefaultProfile();
    }
    return profile;
  }

  /**
   * Updates the profile information.
   *
   * @param profileDetails The updated profile data
   * @return The updated profile entity
   */
  public Profile updateProfile(Profile profileDetails) {
    Profile existingProfile = getProfile();

    // Update fields from profileDetails
    existingProfile.setName(profileDetails.getName());
    existingProfile.setTitle(profileDetails.getTitle());
    existingProfile.setEmail(profileDetails.getEmail());
    existingProfile.setPhone(profileDetails.getPhone());
    existingProfile.setLocation(profileDetails.getLocation());
    existingProfile.setAvatarUrl(profileDetails.getAvatarUrl());
    existingProfile.setBio(profileDetails.getBio());
    existingProfile.setLinkedinUrl(profileDetails.getLinkedinUrl());
    existingProfile.setGithubUrl(profileDetails.getGithubUrl());
    existingProfile.setTwitterUrl(profileDetails.getTwitterUrl());
    existingProfile.setYoutubeUrl(profileDetails.getYoutubeUrl());

    // Update skills if provided
    if (profileDetails.getSkills() != null && !profileDetails.getSkills().isEmpty()) {
      existingProfile.setSkills(profileDetails.getSkills());
    }

    return profileRepository.save(existingProfile);
  }

  public Profile updateSkills(Map<String, String> skills) {
    Profile existingProfile = getProfile();
    existingProfile.setSkills(skills);
    return profileRepository.save(existingProfile);
  };

  /**
   * Creates a default profile with placeholder values. Used when no profile exists in the
   * database.
   *
   * @return A new profile with default values
   */
  private Profile createDefaultProfile() {
    Profile defaultProfile = new Profile();
    defaultProfile.setName("Rajat Kumar");
    defaultProfile.setTitle("Backend Developer");
    defaultProfile.setEmail("rajatkansal2002.@gmail.com");
    defaultProfile.setBio("Experienced backend developer specializing in Java and Spring Boot.");

    // Initialize empty collections
    defaultProfile.setSkills(new HashMap<>());

    return profileRepository.save(defaultProfile);
  }
}