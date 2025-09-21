package com.example.Organic_Challenge.dto;

import com.example.Organic_Challenge.entity.InspectionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionResponseDto {
    private Long id;
    private Long farmId;
    private String farmName;
    private LocalDate date;

    private InspectionStatus status;
    private Double complianceScore;
}
