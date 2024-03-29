package com.yerokha.neotour.controller;

import com.yerokha.neotour.dto.BookingDto;
import com.yerokha.neotour.dto.BookingRequest;
import com.yerokha.neotour.dto.BookingResponse;
import com.yerokha.neotour.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bookings")
@Tag(name = "Booking", description = "Controller for creating bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(
            summary = "Get bookings", description = "Get paged list of bookings",
            tags = {"booking", "get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Should authorize", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Page<BookingDto>> getBookings(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(bookingService.getBookings(authentication.getName(), page, size));
    }

    @Operation(
            summary = "Get booking", description = "Get one booking by id",
            tags = {"booking", "get"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Should authorize", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Booking with this id not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(bookingService.getBooking(id, authentication.getName()));
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
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Not authenticated", content = @Content)
            }
    )
    public BookingResponse addBooking(@RequestBody @Valid BookingRequest dto, Authentication authentication) {
        String username = authentication.getName();
        return bookingService.addBooking(dto, username);
    }

    @Operation(
            summary = "Delete booking", description = "User can delete and cancel his booking",
            tags = {"booking", "delete"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Booking deletion success"),
                    @ApiResponse(responseCode = "404", description = "No booking with given id found", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Authorize before deleting booking", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id, Authentication authentication) {
        bookingService.deleteBooking(id, authentication.getName());
        return ResponseEntity.ok("Booking deleted");
    }
}
