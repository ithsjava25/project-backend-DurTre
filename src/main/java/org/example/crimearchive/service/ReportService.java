package org.example.crimearchive.service;

import org.example.crimearchive.DTO.CreateReport;
import org.example.crimearchive.KNumberService;
import org.example.crimearchive.bevis.Report;
import org.example.crimearchive.mapper.ReportMapper;
import org.example.crimearchive.repository.SimpleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final SimpleRepository simpleRepository;
    private final KNumberService knumberSErvice;


    public ReportService(SimpleRepository simpleRepository, KNumberService kservice) {
        this.simpleRepository = simpleRepository;
        this.knumberSErvice = kservice;
    }

    public void saveReport(CreateReport report) {
        if (report.caseNumber() == null || report.caseNumber().isBlank()) {
            simpleRepository.save(ReportMapper.toEntity(new CreateReport(report.event(), report.name(), knumberSErvice.getCaseNumber())));
        } else {
            String santiziedNumber = caseNumberSanitation(report.caseNumber());
            if (caseNumberExists(santiziedNumber))
                simpleRepository.save(ReportMapper.toEntity(report));
            simpleRepository.save(ReportMapper.toEntity(new CreateReport(report.event(), report.name(), knumberSErvice.getCaseNumber())));
        }
    }

    private String caseNumberSanitation(String caseNumber) {
        if (caseNumber.matches("^\\d{4}-\\d{6}$")) {
            return "K-" + caseNumber;
        } else {
            return caseNumber.toUpperCase();
        }
    }

    public boolean caseNumberExists(String caseNumber) {
        return simpleRepository.existsByCaseNumber(caseNumber);
    }

    public List<Report> getAllReports() {
        return simpleRepository.findAll();
    }
}
