package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Data
public class FarmerResponseDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String county;
}
