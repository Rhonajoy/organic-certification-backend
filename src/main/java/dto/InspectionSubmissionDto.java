package dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class InspectionSubmissionDto {
    @NotNull(message = "Answers are required")
    private List<Boolean> answers;
}
