package com.yerokha.neotour.util;

import com.yerokha.neotour.dto.BookingListRequest;
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

    public static BookingListRequest toListRequest(Booking booking) {
        return new BookingListRequest(
                booking.getId(),
                booking.getTour().getId(),
                booking.getTour().getImages().get(0).getImageUrl(),
                booking.getTour().getTourName()
        );
    }
}