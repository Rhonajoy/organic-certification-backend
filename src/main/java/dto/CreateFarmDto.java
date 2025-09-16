package dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFarmDto {
    @NotNull(message = "Farmer ID is required")
    private Long farmerId;
    @NotBlank(message = "farmN ame is required")
    private String farmName;
    @NotBlank(message = "location is required")
    private String location;
    @NotBlank(message = "areaHa is required")
    @Min(value = 0, message = "Area cannot be negative")
    private Double areaHa;

}
