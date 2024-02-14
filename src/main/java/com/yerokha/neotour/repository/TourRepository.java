package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findTop9ByOrderByViewCountDesc();

    List<Tour> findTop9ByOrderByBookingCountDesc();

    @Query("SELECT t FROM Tour t WHERE BITAND(t.recommendedMonths, :monthMask) > 0")
    Page<Tour> findRecommendedTours(int monthMask, Pageable pageable);

//    Page<Tour> findAllMonthOrderByBookingCountDesc(List<Month> season, Pageable pageable);

}