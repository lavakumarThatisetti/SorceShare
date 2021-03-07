package com.sorceshare.userstore.repository;

import com.sorceshare.userstore.model.ERole;
import com.sorceshare.userstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
