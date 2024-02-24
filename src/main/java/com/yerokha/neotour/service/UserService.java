package com.yerokha.neotour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yerokha.neotour.dto.UpdateProfileRequest;
import com.yerokha.neotour.dto.UserProfile;
import com.yerokha.neotour.entity.AppUser;
import com.yerokha.neotour.repository.UserRepository;
import com.yerokha.neotour.util.UserMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private final UserDetailsServiceImpl userDetailsService;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserService(UserDetailsServiceImpl userDetailsService,
                       ImageService imageService,
                       UserRepository userRepository,
                       ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }


    public UserProfile getUserProfile(String username) {
        return UserMapper.toProfileDto((AppUser) userDetailsService.loadUserByUsername(username));
    }

    public UserProfile updateUserProfile(String username, @Valid String json, MultipartFile image) {
        UpdateProfileRequest request;
        try {
            request = objectMapper.readValue(json, UpdateProfileRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        AppUser user = (AppUser) userDetailsService.loadUserByUsername(username);
        UserMapper.fromProfileDto(user, request);
        if (image != null) {
            user.setProfilePicture(imageService.processImage(image));
        }
        userRepository.save(user);
        return UserMapper.toProfileDto(user);
    }


}
