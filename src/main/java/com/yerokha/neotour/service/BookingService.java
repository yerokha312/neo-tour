package com.yerokha.neotour.service;

import com.yerokha.neotour.dto.BookingDto;
import com.yerokha.neotour.dto.BookingRequest;
import com.yerokha.neotour.dto.BookingResponse;
import com.yerokha.neotour.entity.AppUser;
import com.yerokha.neotour.entity.Booking;
import com.yerokha.neotour.entity.Tour;
import com.yerokha.neotour.exception.NotFoundException;
import com.yerokha.neotour.exception.PhoneNumberAlreadyTakenException;
import com.yerokha.neotour.repository.BookingRepository;
import com.yerokha.neotour.repository.TourRepository;
import com.yerokha.neotour.repository.UserRepository;
import com.yerokha.neotour.util.BookingMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, TourRepository tourRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BookingResponse addBooking(BookingRequest dto, String username) {
        Result tourAndUser = lookUpForTourAndUser(dto, username);
        Booking booking = new Booking();
        booking.setTour(tourAndUser.tour());
        booking.setBookingDate(LocalDateTime.now());
        booking.setAppUser(tourAndUser.appUser());
        String phoneNumber = dto.phoneNumber();
        if (dto.savePhone()) {
            if (phoneNumberAlreadyExists(phoneNumber, tourAndUser)) {
                throw new PhoneNumberAlreadyTakenException("Phone number already taken");
            }
            tourAndUser.appUser().setPhoneNumber(phoneNumber);
        }
        booking.setPhoneNumber(phoneNumber);
        booking.setPeopleCount(dto.peopleCount());
        booking.setComment(dto.comment());
        bookingRepository.save(booking);
        tourRepository.incrementBookingCount(booking.getTour().getId());
        return BookingMapper.toResponse(booking);
    }

    private boolean phoneNumberAlreadyExists(String phoneNumber, Result tourAndUser) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent() && !Objects.equals(
                tourAndUser.appUser().getPhoneNumber(), phoneNumber);
    }

    private Result lookUpForTourAndUser(BookingRequest dto, String username) {
        Tour tour = tourRepository.findById(dto.tourId()).orElseThrow(() -> new NotFoundException("Tour not found"));
        AppUser appUser = userRepository.findByUsernameOrEmailIgnoreCase(username, username).orElseThrow(() ->
                new NotFoundException("User not found"));
        return new Result(tour, appUser);
    }

    private record Result(Tour tour, AppUser appUser) {
    }

    public BookingDto getBooking(Long id, String name) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Booking not found"));
        authorizeRequest(name, booking);
        return BookingMapper.toDto(booking);
    }

    public Page<BookingDto> getBookings(String username, int page, int size) {
        return bookingRepository.findAllByAppUser_Username(username, PageRequest.of(page, size))
                .map(BookingMapper::toDto);
    }

    public void deleteBooking(Long id, String name) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Booking not found"));
        authorizeRequest(name, booking);
        bookingRepository.deleteById(id);
    }

    private static void authorizeRequest(String name, Booking booking) {
        String username = booking.getAppUser().getUsername();
        String email = booking.getAppUser().getEmail();
        if (!(Objects.equals(name, username) || Objects.equals(name, email))) {
            throw new NotFoundException("Booking not found");
        }
    }
}
