package com.loan.demo.controller;

import com.loan.demo.model.LoanApplication;
import com.loan.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // GET /reports/loan-summary
    @GetMapping("/loan-summary")
    public Map<String, Object> getLoanSummary() {
        return reportService.getLoanSummary();
    }

    // GET /reports/emi
    @GetMapping("/emi")
    public Map<String, Double> getEMICollectionReport() {
        return reportService.getEMICollectionReport();
    }

    // GET /reports/overdue
    @GetMapping("/overdue")
    public List<LoanApplication> getOverdueLoans() {
        return reportService.getOverdueLoans();
    }
}
