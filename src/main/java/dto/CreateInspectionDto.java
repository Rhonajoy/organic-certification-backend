package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInspectionDto {
    @NotNull(message = "Farm ID is required")
    private Long farmId;

    @NotBlank(message = "Inspector name is required")
    private String inspectorName;
}
