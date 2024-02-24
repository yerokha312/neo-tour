package com.yerokha.neotour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yerokha.neotour.dto.LoginRequest;
import com.yerokha.neotour.dto.LoginResponse;
import com.yerokha.neotour.dto.RegistrationRequest;
import com.yerokha.neotour.dto.RegistrationResponse;
import com.yerokha.neotour.entity.AppUser;
import com.yerokha.neotour.entity.Image;
import com.yerokha.neotour.entity.Role;
import com.yerokha.neotour.exception.EmailAlreadyTakenException;
import com.yerokha.neotour.exception.NotEnabledException;
import com.yerokha.neotour.exception.PhoneNumberAlreadyTakenException;
import com.yerokha.neotour.exception.UsernameAlreadyTakenException;
import com.yerokha.neotour.repository.RoleRepository;
import com.yerokha.neotour.repository.UserRepository;
import com.yerokha.neotour.util.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ImageService imageService;
    private final ObjectMapper objectMapper;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService, ImageService imageService, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.imageService = imageService;
        this.objectMapper = objectMapper;
    }

    public RegistrationResponse registerUser(String dto, MultipartFile image) {
        RegistrationRequest request;
        try {
            request = objectMapper.readValue(dto, RegistrationRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (usernameExists(request.username())) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        if (emailExists(request.email())) {
            throw new EmailAlreadyTakenException("Email is already taken");
        }
        if (phoneNumberExists(request.phoneNumber())) {
            throw new PhoneNumberAlreadyTakenException("Email is already taken");
        }


        AppUser appUser = UserMapper.fromDto(request);
        appUser.setProfilePicture(imageService.processImage(image));
        String encodedPassword = passwordEncoder.encode(request.password());
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = Set.of(userRole);
        appUser.setPassword(encodedPassword);
        appUser.setAuthorities(authorities);
        return UserMapper.toDto(userRepository.save(appUser));
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmailIgnoreCase(email).isPresent();
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsernameOrEmailIgnoreCase(username, username).isPresent();
    }

    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            String token = tokenService.generateToken(authentication);

            AppUser appUser = (AppUser) authentication.getPrincipal();
            Image profilePicture = appUser.getProfilePicture();
            return new LoginResponse(
                    appUser.getUsername(),
                    appUser.getFirstName(),
                    appUser.getLastName(),
                    appUser.getPhoneNumber(),
                    appUser.getEmail(),
                    profilePicture == null ? null : profilePicture.getImageUrl(),
                    token);

        } catch (AuthenticationException e) {
            if (e instanceof DisabledException) {
                throw new NotEnabledException("Account has not been enabled");
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        }
    }
}



































