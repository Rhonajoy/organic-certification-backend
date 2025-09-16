package dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFieldDto {
    @NotNull(message = "Farm Id is required")
    private Long farmId;
    @NotBlank(message = " Field name is required")
    private String name;
    @NotBlank(message = "crop type  is required")
    private String crop;
    @NotBlank(message = "areaHa is required")
    @Min(value = 0, message = "Area cannot be negative")
    private Double areaHa;


}
