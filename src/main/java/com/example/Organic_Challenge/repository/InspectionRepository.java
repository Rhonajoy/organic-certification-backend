package com.example.Organic_Challenge.repository;

import com.example.Organic_Challenge.entity.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionRepository extends JpaRepository<Inspection,Long> {
    List<Inspection> findByFarmId(Long farmId);
}
