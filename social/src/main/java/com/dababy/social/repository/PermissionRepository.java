package com.dababy.social.repository;

import com.dababy.social.domain.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<UserPermission,String> {

}
