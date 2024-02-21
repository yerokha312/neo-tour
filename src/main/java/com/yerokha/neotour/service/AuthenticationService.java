package com.yerokha.neotour.service;

import com.yerokha.neotour.dto.LoginRequest;
import com.yerokha.neotour.dto.LoginResponse;
import com.yerokha.neotour.dto.RegistrationRequest;
import com.yerokha.neotour.entity.AppUser;
import com.yerokha.neotour.entity.Role;
import com.yerokha.neotour.exception.EmailAlreadyTakenException;
import com.yerokha.neotour.exception.NotEnabledException;
import com.yerokha.neotour.exception.UsernameAlreadyTakenException;
import com.yerokha.neotour.repository.RoleRepository;
import com.yerokha.neotour.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public void registerUser(RegistrationRequest request) {
        String username = request.username();
        if (usernameExists(username)) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        String email = request.email();
        if (emailExists(email)) {
            throw new EmailAlreadyTakenException("Email is already taken");
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
        appUser.setEmail(email.toLowerCase());
        userRepository.save(appUser);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmailIgnoreCase(email).isPresent();
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isPresent();
    }

    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            String token = tokenService.generateToken(authentication);

            return new LoginResponse(request.username(), token);

        } catch (AuthenticationException e) {
            if (e instanceof DisabledException) {
                throw new NotEnabledException("Account has not been enabled");
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        }
    }
}



































