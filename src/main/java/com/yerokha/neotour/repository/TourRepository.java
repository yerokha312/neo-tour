package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TourRepository extends JpaRepository<Tour, Long> {

    Page<Tour> findAllByOrderByViewCountDesc(Pageable pageable);

    Page<Tour> findAllByOrderByBookingCountDesc(Pageable pageable);

    @Query("SELECT t FROM Tour t WHERE (LOWER(t.location.continent) = LOWER(:continent) AND BITAND(t.recommendedMonths, :monthMask) > 0)")
    Page<Tour> findAllByLocation_Continent(String continent, Pageable pageable, int monthMask);

    @Query("SELECT t FROM Tour t WHERE BITAND(t.recommendedMonths, :monthMask) > 0")
    Page<Tour> findRecommendedTours(int monthMask, Pageable pageable);

    @Query("SELECT t FROM Tour t WHERE (t.isFeatured = true AND BITAND(t.recommendedMonths, :monthMask) > 0)")
    Page<Tour> findAllByFeaturedTrue(int monthMask, Pageable pageable);

    @Modifying
    @Query("UPDATE Tour t SET t.viewCount = t.viewCount + 1 WHERE t.id = :id")
    void incrementViewCount(Long id);

    @Modifying
    @Query("UPDATE Tour t SET t.bookingCount = t.bookingCount + 1 WHERE t.id = :id")
    void incrementBookingCount(Long id);


}
