package com.yerokha.neotour.repository;

import com.yerokha.neotour.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
