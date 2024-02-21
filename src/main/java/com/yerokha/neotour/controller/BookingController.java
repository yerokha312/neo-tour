package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.CreateBookingDto;
import com.yerokha.neotour.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bookings")
@PreAuthorize(value = "hasAuthority('USER')")
@Tag(name = "Booking", description = "Controller for creating bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add a new booking",
            description = "Add a new booking to the database",
            tags = {"booking", "post"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Booking added successfully",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    public void addBooking(@RequestBody @Valid CreateBookingDto dto, Authentication authentication) {
        String username = authentication.getName();
        bookingService.addBooking(dto, username);
    }
}
