package com.yerokha.neotour.service;

import com.yerokha.neotour.dto.RegistrationRequest;
import com.yerokha.neotour.entity.AppUser;
import com.yerokha.neotour.entity.Role;
import com.yerokha.neotour.exception.UsernameAlreadyTakenException;
import com.yerokha.neotour.repository.RoleRepository;
import com.yerokha.neotour.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegistrationRequest request) {
        String username = request.username();
        if (usernameExists(username)) {
            throw new UsernameAlreadyTakenException();
        }
        String email = request.email();
        if (emailExists(email)) {
            throw new UsernameAlreadyTakenException();
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = Set.of(userRole);
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(encodedPassword);
        appUser.setAuthorities(authorities);
        appUser.setFirstName(request.firstName());
        appUser.setLastName(request.lastName());
        appUser.setEmail(email);
        userRepository.save(appUser);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}



































