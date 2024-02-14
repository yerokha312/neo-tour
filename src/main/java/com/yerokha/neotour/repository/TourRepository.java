package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.Month;
import com.yerokha.neotour.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findTop9ByOrderByViewCountDesc();

    List<Tour> findTop9ByOrderByBookingCountDesc();

    Page<Tour> findAllMonthOrderByBookingCountDesc(List<Month> season, Pageable pageable);

}
