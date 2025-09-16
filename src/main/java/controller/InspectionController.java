package controller;

import dto.CreateInspectionDto;
import dto.InspectionResponseDto;
import dto.InspectionSubmissionDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.InspectionService;

@RestController
@RequestMapping("/organic-certified/inspection")
public class InspectionController {
    @Autowired
    private InspectionService inspectionService;

    @PostMapping("/start")
    public ResponseEntity<InspectionResponseDto> startInspection( @Valid @RequestBody CreateInspectionDto dto) {
        InspectionResponseDto startedInspection = inspectionService.startInspection(dto);
        return new ResponseEntity<>(startedInspection, HttpStatus.CREATED);
    }

    @PostMapping("/submit/{inspectionId}")
    public ResponseEntity<InspectionResponseDto> submitInspection(@PathVariable Long inspectionId,@Valid @RequestBody InspectionSubmissionDto dto) {
        InspectionResponseDto submittedInspection = inspectionService.submitInspection(inspectionId, dto);
        return ResponseEntity.ok(submittedInspection);
    }

}
