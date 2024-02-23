package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByHash(String hashString);
}
