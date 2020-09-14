package com.dababy.social.repository;

import com.dababy.social.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, UUID> {

    @Query("SELECT u FROM ApplicationUser u JOIN fetch u.roles Where u.email=:email")
    ApplicationUser findByEmail(String email);
}
