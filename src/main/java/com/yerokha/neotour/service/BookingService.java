package com.yerokha.neotour.service;

import com.yerokha.neotour.dto.CreateBookingDto;
import com.yerokha.neotour.entity.Booking;
import com.yerokha.neotour.exception.NotFoundException;
import com.yerokha.neotour.repository.BookingRepository;
import com.yerokha.neotour.repository.TourRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository;

    public BookingService(BookingRepository bookingRepository, TourRepository tourRepository) {
        this.bookingRepository = bookingRepository;
        this.tourRepository = tourRepository;
    }

    @Transactional
    public void addBooking(CreateBookingDto dto) {
        Booking booking = new Booking();
        booking.setTour(tourRepository.findById(dto.tourId()).orElseThrow(NotFoundException::new));
        booking.setBookingDate(LocalDateTime.now());
        booking.setPhoneNumber(dto.phoneNumber());
        booking.setPeopleCount(dto.peopleCount());
        booking.setComment(dto.comment());
        bookingRepository.save(booking);
        tourRepository.incrementBookingCount(booking.getTour().getId());
    }
}
