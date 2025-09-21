package com.example.Organic_Challenge.repository;

import com.example.Organic_Challenge.entity.Inspection;
import com.example.Organic_Challenge.entity.InspectionStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionRepository extends JpaRepository<Inspection,Long> {
    List<Inspection> findByFarmId(Long farmId);
    Inspection findTopByFarmIdOrderByDateDesc(Long farmId);
    List<Inspection> findByStatus(InspectionStatus status);
    long countByStatus(InspectionStatus status);

    List<Inspection> findAllByOrderByDateDesc(Pageable pageable);


}
