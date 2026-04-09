package org.example.crimearchive.service;

import org.example.crimearchive.DTO.CreateReport;
import org.example.crimearchive.KNumberService;
import org.example.crimearchive.bevis.Cases;
import org.example.crimearchive.bevis.Report;
import org.example.crimearchive.permissions.PermissionRepository;
import org.example.crimearchive.polis.Account;
import org.example.crimearchive.repository.SimpleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportService {

    private final SimpleRepository simpleRepository;
    private final KNumberService knumberService;
    private final PermissionRepository permissionRepository;

    public ReportService(SimpleRepository simpleRepository, KNumberService kservice, PermissionRepository permissionRepository) {
        this.simpleRepository = simpleRepository;
        this.knumberService = kservice;
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public void saveReport(CreateReport report, Account currentUser) {
        Cases cases;

        if (report.caseNumber() == null || report.caseNumber().isBlank()) {
            String newCaseNumber = knumberService.getKNumber();
            cases = new Cases(newCaseNumber);

            cases.getAccounts().add(currentUser);

            permissionRepository.save(cases);
        } else {
            String sanitized = caseNumberSanitation(report.caseNumber());
            cases = permissionRepository.findFirstByCaseNumber(sanitized)
                    .orElseThrow(() -> new RuntimeException("Case not found: " + sanitized));
        }

        Report newReport = new Report(UUID.randomUUID(), report.name(), report.event(), cases);
        simpleRepository.save(newReport);
    }

    private String caseNumberSanitation(String caseNumber) {
        if (caseNumber.matches("^\\d{4}-\\d{6}$")) {
            return "K-" + caseNumber;
        } else {
            return caseNumber.toUpperCase();
        }
    }

    public List<Report> getAllReports() {
        return simpleRepository.findAll();
    }

    public long getAmount() {
        return simpleRepository.count();
    }
}