package com.loan.demo.controller;

import com.loan.demo.model.LoanApplication;
import com.loan.demo.service.LoanApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class LoanApprovalController {

    @Autowired
    private LoanApprovalService loanApprovalService;

    // Get all pending loan applications
    @GetMapping("/pending")
    public List<LoanApplication> getPendingApplications() {
        return loanApprovalService.getPendingApplications();
    }

    // Reject loan by ID
    @PutMapping("/reject/{id}")
    public Map<String, Object> rejectLoan(@PathVariable Long id) {
        Optional<LoanApplication> rejectedLoan = loanApprovalService.rejectLoan(id);

        if (rejectedLoan.isPresent()) {
            return Map.of("success", true, "loan", rejectedLoan.get());
        } else {
            return Map.of("success", false, "message", "Loan application not found");
        }
    }
    

}
