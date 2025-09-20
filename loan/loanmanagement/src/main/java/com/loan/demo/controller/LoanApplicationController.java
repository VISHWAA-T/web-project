package com.loan.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loan.demo.model.LoanApplication;
import com.loan.demo.service.LoanApplicationService;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RestController
@RequestMapping("/loans")
public class LoanApplicationController {
	
	@Autowired
    private final LoanApplicationService service;

    public LoanApplicationController(LoanApplicationService service) {
        this.service = service;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyLoan(@RequestBody LoanApplication loan) {
        return ResponseEntity.ok(service.applyForLoan(loan));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanApplication>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getLoansByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<LoanApplication>> getApprovedLoans(@RequestParam String status) {
        return ResponseEntity.ok(service.getAllApprovedLoans());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateLoanStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
    	 String status = body.get("status");
        Optional<LoanApplication> updated = service.updateStatus(id, status);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
