package service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import dto.CertificateResponseDto;
import dto.CreateCertificateDto;
import entity.Certificate;
import entity.Farm;
import entity.Inspection;
import exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import repository.CertificateRepository;
import repository.FarmRepository;
import repository.InspectionRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private InspectionRepository inspectionRepository;

    public CertificateResponseDto generateCertificate(CreateCertificateDto dto) throws DocumentException, IOException {
        Farm farm = farmRepository.findById(dto.getFarmId()).orElseThrow(() -> new  ResourceNotFoundException("Farm not found"));

        Optional<Inspection> latestInspectionOpt = inspectionRepository.findByFarmId(farm.getId())
                .stream()
                .max(Comparator.comparing(Inspection::getDate));

        if (latestInspectionOpt.isEmpty()) {
            throw new RuntimeException("No inspections found for this farm.");
        }

        Inspection latestInspection = latestInspectionOpt.get();


        if (!"Approved".equals(latestInspection.getStatus())) {
            throw new RuntimeException("Farm is not approved for organic certification. Compliance score: " + latestInspection.getComplianceScore() + "%");
        }

        String filePath = "certificates/organic_certificate_" + dto.getFarmId() + ".pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();


        document.add(new Paragraph("ORGANIC CERTIFIED "));
        document.add(new Paragraph("Farmer Name: " + farm.getFarmer().getName()));
        document.add(new Paragraph("Farm Name: " + farm.getFarmName()));
        document.add(new Paragraph("Farm Location: " + farm.getLocation()));
        document.add(new Paragraph("Farm Area: " + farm.getAreaHa()));
        document.add(new Paragraph("Checklist Score: " + latestInspection.getComplianceScore() + "%"));


        String certificateNumber = "ORG-CERT-" + UUID.randomUUID().toString();
        document.add(new Paragraph("Certificate Number: " + certificateNumber));
        document.add(new Paragraph("Issue Date: " + LocalDate.now()));
        document.add(new Paragraph("Expiry Date: " + LocalDate.now().plusYears(1)));
        document.add(new Paragraph(" "));


        document.add(new Paragraph("-------------------------------------"));
        document.add(new Paragraph("  “Certified Organic” Badge Placeholder  "));
        document.add(new Paragraph("-------------------------------------"));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("-------------------------------------"));
        document.add(new Paragraph(" Inspector Signature Placeholder "));
        document.add(new Paragraph("-------------------------------------"));

        document.close();

        Certificate certificate = new Certificate();
        certificate.setFarm(farm);
        certificate.setCertificateNo(certificateNumber);
        certificate.setIssueDate(LocalDate.now());
        certificate.setExpiryDate(LocalDate.now().plusYears(1));
        certificate.setPdfUrl(filePath);

        Certificate savedCertificate = certificateRepository.save(certificate);


        CertificateResponseDto responseDto = new CertificateResponseDto();
        responseDto.setId(savedCertificate.getId());
        responseDto.setFarmId(savedCertificate.getFarm().getId());
        responseDto.setCertificateNo(savedCertificate.getCertificateNo());
        responseDto.setIssueDate(savedCertificate.getIssueDate());
        responseDto.setExpiryDate(savedCertificate.getExpiryDate());
        responseDto.setPdfUrl(savedCertificate.getPdfUrl());
        return responseDto;
    }
}
