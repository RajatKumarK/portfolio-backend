package com.rajatkumar.portfolio.portfolio_backend.dao.api;

import com.rajatkumar.portfolio.portfolio_backend.dao.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  // Usually only one profile in the system
  Profile findFirstByOrderById();
}
