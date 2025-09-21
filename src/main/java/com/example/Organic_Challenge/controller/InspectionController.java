package com.example.Organic_Challenge.controller;

import com.example.Organic_Challenge.dto.CreateInspectionDto;
import com.example.Organic_Challenge.dto.FarmerResponseDto;
import com.example.Organic_Challenge.dto.InspectionResponseDto;
import com.example.Organic_Challenge.dto.InspectionSubmissionDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Organic_Challenge.service.InspectionService;

import java.util.List;

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
    @GetMapping
    public ResponseEntity<Page<InspectionResponseDto>> getAllInspections(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return  ResponseEntity.ok(inspectionService.getAllInspections(pageable));
    }

    @GetMapping("/draft/count")
    public long getDraftInspectionCount() {
        return inspectionService.countDraftInspections();
    }

    @GetMapping("/recent")
    public List<InspectionResponseDto> getRecentInspections(@RequestParam(defaultValue = "10") int limit) {
        return inspectionService.getRecentInspectionsDto(limit);
    }

}
