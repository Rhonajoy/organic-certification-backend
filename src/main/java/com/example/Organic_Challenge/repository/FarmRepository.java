package com.example.Organic_Challenge.repository;

import com.example.Organic_Challenge.entity.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm,Long> {
    Page<Farm> findByFarmerId(Long farmerId, Pageable pageable);
}
