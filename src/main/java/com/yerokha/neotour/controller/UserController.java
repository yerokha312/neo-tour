package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.UserProfile;
import com.yerokha.neotour.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User", description = "Protected controller for users")
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get user profile", description = "Watch own information", tags = {"user", "get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @GetMapping
    public UserProfile getUserProfile(Authentication authentication) {
        return userService.getUserProfile(authentication.getName());
    }

    @Operation(
            summary = "Get user profile", description = "Watch own information", tags = {"user", "get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @PutMapping
    public UserProfile updateUserProfile(Authentication authentication,
                                         @RequestPart @Valid String request,
                                         @RequestPart(value = "image", required = false) MultipartFile image) {
        return userService.updateUserProfile(authentication.getName(), request, image);
    }
}
