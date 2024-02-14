package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Image, Long> {
}
