package com.sorceshare.userstore.repository;

import com.sorceshare.userstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

   Optional<User> findById(Long id);

   Optional<User> findByUserName(String userName);

   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "UPDATE users u SET active = :active WHERE u.id = :id",nativeQuery = true)
   int disableActiveProfile(@Param("active") boolean active, @Param("id") long id);

   Boolean existsByUserName(String userName);

   Boolean existsByEmail(String email);

}
