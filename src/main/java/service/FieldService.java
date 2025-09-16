package service;

import dto.CreateFieldDto;
import dto.FarmResponseDto;
import dto.FieldResponseDto;
import entity.Farm;
import entity.Field;
import exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import repository.FarmRepository;
import repository.FieldRepository;

public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private FarmRepository farmRepository;

    public FieldResponseDto createField(CreateFieldDto dto) {
        Farm farm = farmRepository.findById(dto.getFarmId()).orElseThrow(() -> new ResourceNotFoundException("Farm not found"));
        Field field = new Field(null, farm, dto.getName(), dto.getCrop(), dto.getAreaHa());
        Field savedField = fieldRepository.save(field);
        return toFieldResponseDto(savedField);
    }

    public Page<FieldResponseDto> getFieldsByFarmId(Long farmId,Pageable pageable) {
        Page<Field> fieldPage = fieldRepository.findByFarmId(farmId, pageable);
        return fieldPage.map(this::toFieldResponseDto);
    }

    public FieldResponseDto getFieldById(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Field not found"));
        return toFieldResponseDto(field);
    }
    public FieldResponseDto updateField(Long id, CreateFieldDto dto) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Field not found"));
        field.setName(dto.getName());
        field.setCrop(dto.getCrop());
        field.setAreaHa(dto.getAreaHa());
        Field updatedField = fieldRepository.save(field);
        return toFieldResponseDto(updatedField);
    }

    public void deleteField(Long id) {
        fieldRepository.deleteById(id);
    }

    private FieldResponseDto toFieldResponseDto(Field entity) {
        return new FieldResponseDto(entity.getId(), entity.getName(), entity.getCrop(), entity.getAreaHa(), entity.getFarm().getId());
    }
}
