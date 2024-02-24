package com.yerokha.neotour.util;

import com.yerokha.neotour.dto.BookingListDto;
import com.yerokha.neotour.dto.BookingResponse;
import com.yerokha.neotour.entity.Booking;

public class BookingMapper {

    public static BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getTour().getId(),
                booking.getTour().getTourName(),
                booking.getBookingDate(),
                booking.getPhoneNumber(),
                booking.getAppUser().getUsername(),
                booking.getAppUser().getFirstName(),
                booking.getAppUser().getLastName(),
                booking.getComment()
        );
    }

    public static BookingListDto toListDto(Booking booking) {
        return new BookingListDto(
                booking.getId(),
                booking.getTour().getId(),
                booking.getBookingDate(),
                booking.getTour().getImages().get(0).getImageUrl(),
                booking.getTour().getTourName()
        );
    }
}