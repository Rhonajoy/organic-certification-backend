package com.example.Organic_Challenge.service;

import com.example.Organic_Challenge.entity.InspectionStatus;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.example.Organic_Challenge.dto.CertificateResponseDto;
import com.example.Organic_Challenge.dto.CreateCertificateDto;
import com.example.Organic_Challenge.entity.Certificate;
import com.example.Organic_Challenge.entity.Farm;
import com.example.Organic_Challenge.entity.Inspection;
import com.example.Organic_Challenge.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Organic_Challenge.repository.CertificateRepository;
import com.example.Organic_Challenge.repository.FarmRepository;
import com.example.Organic_Challenge.repository.InspectionRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private InspectionRepository inspectionRepository;


    public CertificateResponseDto generateCertificate(CreateCertificateDto dto) throws DocumentException, IOException {
    Inspection inspection = inspectionRepository.findById(dto.getInspectionId())
            .orElseThrow(() -> new ResourceNotFoundException("Inspection not found"));

    if (inspection.getStatus() != InspectionStatus.APPROVED) {
        throw new RuntimeException("Farm is not approved. Score: " + inspection.getComplianceScore() + "%");
    }

    Farm farm = inspection.getFarm();

    String folderPath = "certificates";
    File folder = new File(folderPath);
    if (!folder.exists()) {
        folder.mkdirs();
    }
    String filePath = folderPath + "/organic_certificate_" + dto.getInspectionId() + ".pdf";
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(filePath));
    document.open();

    document.add(new Paragraph("ORGANIC CERTIFIED"));
    document.add(new Paragraph("Farmer Name: " + farm.getFarmer().getName()));
    document.add(new Paragraph("Farm Name: " + farm.getFarmName()));
    document.add(new Paragraph("Farm Location: " + farm.getLocation()));
    document.add(new Paragraph("Farm Area: " + farm.getAreaHa()));
    document.add(new Paragraph("Checklist Score: " + inspection.getComplianceScore() + "%"));

    String certificateNumber = "ORG-CERT-" + UUID.randomUUID();
    document.add(new Paragraph("Certificate Number: " + certificateNumber));
    document.add(new Paragraph("Issue Date: " + LocalDate.now()));
    document.add(new Paragraph("Expiry Date: " + LocalDate.now().plusYears(1)));

    document.close();

        Optional<Certificate> existing = certificateRepository.findByFarmId(farm.getId());

        Certificate certificate;
        if (existing.isPresent()) {
            certificate = existing.get();
            certificate.setCertificateNo(certificateNumber);
            certificate.setIssueDate(LocalDate.now());
            certificate.setExpiryDate(LocalDate.now().plusYears(1));
            certificate.setPdfUrl(filePath);
        } else {
            certificate = new Certificate();
            certificate.setFarm(farm);
            certificate.setCertificateNo(certificateNumber);
            certificate.setIssueDate(LocalDate.now());
            certificate.setExpiryDate(LocalDate.now().plusYears(1));
            certificate.setPdfUrl(filePath);
        }


//        Certificate certificate = new Certificate();
//    certificate.setFarm(farm);
//    certificate.setCertificateNo(certificateNumber);
//    certificate.setIssueDate(LocalDate.now());
//    certificate.setExpiryDate(LocalDate.now().plusYears(1));
//    certificate.setPdfUrl(filePath);

    Certificate saved = certificateRepository.save(certificate);

    CertificateResponseDto response = new CertificateResponseDto();
    response.setId(saved.getId());
    response.setFarmId(farm.getId());
    response.setCertificateNo(saved.getCertificateNo());
    response.setIssueDate(saved.getIssueDate());
    response.setExpiryDate(saved.getExpiryDate());
    response.setPdfUrl(saved.getPdfUrl());

    return response;
}

}
