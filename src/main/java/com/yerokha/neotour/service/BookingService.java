package com.yerokha.neotour.service;

import com.yerokha.neotour.dto.CreateBookingDto;
import com.yerokha.neotour.entity.Booking;
import com.yerokha.neotour.exception.NotFoundException;
import com.yerokha.neotour.repository.BookingRepository;
import com.yerokha.neotour.repository.TourRepository;
import com.yerokha.neotour.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public void addBooking(CreateBookingDto dto, String username) {
        Booking booking = new Booking();
        booking.setTour(tourRepository.findById(dto.tourId()).orElseThrow(NotFoundException::new));
        booking.setBookingDate(LocalDateTime.now());
        booking.setAppUser(userRepository.findByUsernameIgnoreCase(username).orElseThrow(NotFoundException::new));
        booking.setPeopleCount(dto.peopleCount());
        booking.setComment(dto.comment());
        bookingRepository.save(booking);
        tourRepository.incrementBookingCount(booking.getTour().getId());
    }
}
