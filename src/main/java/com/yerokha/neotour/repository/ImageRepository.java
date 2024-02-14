package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
