package com.sorceshare.authserver.repository;

import com.sorceshare.authserver.model.ERole;
import com.sorceshare.authserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
