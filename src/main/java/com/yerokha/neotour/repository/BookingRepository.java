package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
