package com.rajatkumar.portfolio.portfolio_backend;

import com.rajatkumar.portfolio.portfolio_backend.api.ProfileRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
  @Autowired
  private ProfileRepository profileRepository;

  public Profile getProfile() {
    return profileRepository.findFirstByOrderById();
  }

  public Profile updateProfile(Profile profileDetails) {
    Profile profile = getProfile();
    // Update fields
    return profileRepository.save(profile);
  }

  public Profile updateSkills(Map<String, String> skills) {
    Profile profile = getProfile();
    profile.setSkills(skills);
    return profileRepository.save(profile);
  }
}
