package org.example.crimearchive.service;

import org.example.crimearchive.DTO.CreateReport;
import org.example.crimearchive.bevis.Report;
import org.example.crimearchive.mapper.ReportMapper;
import org.example.crimearchive.repository.SimpleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    private final SimpleRepository simpleRepository;



    public ReportService(SimpleRepository simpleRepository) {
        this.simpleRepository = simpleRepository;
    }

    public void saveReport(CreateReport report) {
        String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY"));
        System.out.println(currentYear);
        //String lastCaseNumber = simpleRepository.getLatestCaseNumber();
        simpleRepository.save(ReportMapper.toEntity(report));
    }

    public List<Report> getAllReports() {
        return simpleRepository.findAll();
    }
}
