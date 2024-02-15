package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByTour_Id(Long tourId, Pageable pageable);
}
