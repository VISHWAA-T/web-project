package com.loan.demo.service;

import com.loan.demo.model.LoanApplication;
import com.loan.demo.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanApprovalService {

    @Autowired
    private LoanApplicationRepository loanRepo;

    // Get all pending loans
    public List<LoanApplication> getPendingApplications() {
        return loanRepo.findByStatus("PENDING");
    }

    // Approve loan, set interest rate and EMI terms
    public Optional<LoanApplication> approveLoan(Long loanId, double interestRate, int emiMonths) {
        Optional<LoanApplication> optionalLoan = loanRepo.findById(loanId);
        if (optionalLoan.isPresent()) {
            LoanApplication loan = optionalLoan.get();
            loan.setStatus("APPROVED");
            loan.setInterestRate(interestRate);
            loan.setEmiMonths(emiMonths);
            loanRepo.save(loan);
            return Optional.of(loan);
        }
        return Optional.empty();
    }

    // Reject loan application
    public Optional<LoanApplication> rejectLoan(Long loanId) {
        Optional<LoanApplication> optionalLoan = loanRepo.findById(loanId);
        if (optionalLoan.isPresent()) {
            LoanApplication loan = optionalLoan.get();
            loan.setStatus("REJECTED");
            loanRepo.save(loan);
            return Optional.of(loan);
        }
        return Optional.empty();
    }
    
    public Optional<LoanApplication> findById(Long id) {
        return loanRepo.findById(id);
    }

    public LoanApplication save(LoanApplication loan) {
        return loanRepo.save(loan);
    }

}
