package dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data

public class CreateFarmerDto {
    @NotBlank(message = " Farmer Name is required")
    private String name;
    @NotBlank(message = "Phone Number is required")
    private String phone ;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "County is required")
    private String county;

}
