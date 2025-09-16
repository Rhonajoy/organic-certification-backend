package dto;

import entity.InspectionStatus;
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
