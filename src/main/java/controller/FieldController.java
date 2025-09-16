package controller;

import dto.CreateFieldDto;
import dto.FieldResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FieldService;

@RestController
@RequestMapping("/organic-certified/farmers")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PostMapping
    public ResponseEntity<FieldResponseDto> createField(@Valid @RequestBody CreateFieldDto dto) {
        FieldResponseDto createdField = fieldService.createField(dto);
        return new ResponseEntity<>(createdField, HttpStatus.CREATED);
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<Page<FieldResponseDto>> getFieldsByFarmId(@PathVariable Long farmId, Pageable pageable) {
        Page<FieldResponseDto> fields = fieldService.getFieldsByFarmId(farmId, pageable);
        return ResponseEntity.ok(fields);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldResponseDto> getFieldById(@PathVariable Long id) {
        FieldResponseDto field = fieldService.getFieldById(id);
        return ResponseEntity.ok(field);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldResponseDto> updateField(@PathVariable Long id, @RequestBody CreateFieldDto dto) {
        FieldResponseDto updatedField = fieldService.updateField(id, dto);
        return ResponseEntity.ok(updatedField);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable Long id) {
        fieldService.deleteField(id);
        return ResponseEntity.noContent().build();
    }

}
