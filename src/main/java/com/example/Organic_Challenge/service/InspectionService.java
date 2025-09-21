package com.example.Organic_Challenge.service;

import com.example.Organic_Challenge.dto.CreateInspectionDto;
import com.example.Organic_Challenge.dto.FieldResponseDto;
import com.example.Organic_Challenge.dto.InspectionResponseDto;
import com.example.Organic_Challenge.dto.InspectionSubmissionDto;
import com.example.Organic_Challenge.entity.Farm;
import com.example.Organic_Challenge.entity.Field;
import com.example.Organic_Challenge.entity.Inspection;
import com.example.Organic_Challenge.entity.InspectionStatus;
import com.example.Organic_Challenge.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Organic_Challenge.repository.FarmRepository;
import com.example.Organic_Challenge.repository.InspectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InspectionService {
    @Autowired
    private InspectionRepository inspectionRepository;
    @Autowired
    private FarmRepository farmRepository;

    public static final double MIN_COMPLIANCE_SCORE = 80.0;
    public InspectionResponseDto startInspection(CreateInspectionDto dto) {
        Farm farm = farmRepository.findById(dto.getFarmId()).orElseThrow(() -> new ResourceNotFoundException("Farm not found"));

        Inspection inspection = new Inspection();
        inspection.setFarm(farm);
        inspection.setDate(LocalDate.now());

        inspection.setStatus(InspectionStatus.DRAFT);
        Inspection savedInspection = inspectionRepository.save(inspection);


        InspectionResponseDto responseDto = new InspectionResponseDto();
        responseDto.setId(savedInspection.getId());
        responseDto.setFarmId(savedInspection.getFarm().getId());
        responseDto.setFarmName(savedInspection.getFarm().getFarmName());
        responseDto.setDate(savedInspection.getDate());
        responseDto.setStatus(savedInspection.getStatus());
        responseDto.setComplianceScore(savedInspection.getComplianceScore());
        return responseDto;
    }

    public InspectionResponseDto submitInspection(Long inspectionId, InspectionSubmissionDto dto) {
        Inspection inspection = inspectionRepository.findById(inspectionId).orElseThrow(() -> new RuntimeException("Inspection not found"));

        long yesCount = dto.getAnswers().stream().filter(Boolean::booleanValue).count();
        double score = ((double) yesCount / dto.getAnswers().size()) * 100;

        inspection.setComplianceScore(score);
        if (score >= MIN_COMPLIANCE_SCORE) {
            inspection.setStatus(InspectionStatus.APPROVED);
        } else {
            inspection.setStatus(InspectionStatus.REJECTED);
        }



        Inspection submittedInspection = inspectionRepository.saveAndFlush(inspection);



        InspectionResponseDto responseDto = new InspectionResponseDto();
        responseDto.setId(submittedInspection.getId());
        responseDto.setFarmId(submittedInspection.getFarm().getId());
        responseDto.setDate(submittedInspection.getDate());

        responseDto.setStatus(submittedInspection.getStatus());
        responseDto.setComplianceScore(submittedInspection.getComplianceScore());
        return responseDto;
    }

    public Page<InspectionResponseDto> getAllInspections(Pageable pageable) {
        return inspectionRepository.findAll(pageable)
                .map(this::toInspectionResponseDto);

    }
    public List<Inspection> getDraftInspections() {
        return inspectionRepository.findByStatus(InspectionStatus.DRAFT);
    }

    public List<InspectionResponseDto> getDraftInspectionsDto() {
        return getDraftInspections()
                .stream()
                .map(this::toInspectionResponseDto)
                .toList();
    }
    public long countDraftInspections() {
        return inspectionRepository.countByStatus(InspectionStatus.DRAFT);
    }



    public List<InspectionResponseDto> getRecentInspectionsDto(int limit) {
        return inspectionRepository
                .findAllByOrderByDateDesc(PageRequest.of(0, limit))
                .stream()
                .map(this::toInspectionResponseDto)
                .toList();
    }

    private InspectionResponseDto toInspectionResponseDto(Inspection entity) {
        return new InspectionResponseDto(entity.getId(), entity.getFarm().getId(), entity.getFarm().getFarmName(),entity.getDate(),entity.getStatus(),entity.getComplianceScore());
    }
}
