package com.example.Organic_Challenge.dto;

import com.example.Organic_Challenge.entity.InspectionStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InspectionResponseDto {
    private Long id;
    private Long farmId;
    private LocalDate date;
    private String inspectorName;
    private InspectionStatus status;
    private Double complianceScore;
}
