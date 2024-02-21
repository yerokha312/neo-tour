package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsernameIgnoreCase(String username);

    //    Optional<AppUser> findByUsernameOrEmail(String username, String email);
    Optional<AppUser> findByEmailIgnoreCase(String email);
}
