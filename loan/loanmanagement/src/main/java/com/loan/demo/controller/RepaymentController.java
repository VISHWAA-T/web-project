package com.loan.demo.controller;

import com.loan.demo.model.Repayment;
import com.loan.demo.service.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repayments")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class RepaymentController {

    @Autowired
    private RepaymentService repaymentService;

    // Get repayments by loan ID
    @GetMapping("/loan/{loanId}")
    public List<Repayment> getRepaymentsByLoan(@PathVariable Long loanId) {
        return repaymentService.getRepaymentsByLoanId(loanId);
    }

    // Add a repayment
    @PostMapping("")
    public Repayment addRepayment(@RequestBody Repayment repayment) {
        return repaymentService.addRepayment(repayment);
    }

    // Optional: Delete repayment
    @DeleteMapping("/{id}")
    public void deleteRepayment(@PathVariable Long id) {
        repaymentService.deleteRepayment(id);
    }
}
