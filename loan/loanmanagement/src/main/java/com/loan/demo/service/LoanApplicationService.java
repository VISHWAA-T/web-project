package com.loan.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.demo.model.LoanApplication;
import com.loan.demo.repository.LoanApplicationRepository;

@Service
public class LoanApplicationService {

	@Autowired
    private final LoanApplicationRepository repository;

    public LoanApplicationService(LoanApplicationRepository repository) {
        this.repository = repository;
    }

    public LoanApplication applyForLoan(LoanApplication loan) {
        loan.setStatus("PENDING");
        return repository.save(loan);
    }

    public List<LoanApplication> getAllApprovedLoans() {
        return repository.findByStatus("APPROVED");
    }

    public List<LoanApplication> getLoansByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public Optional<LoanApplication> updateStatus(Long id, String status) {
        return repository.findById(id).map(loan -> {
            loan.setStatus(status);

            if (status.equalsIgnoreCase("APPROVED")) {
                double interestRate = 10.5;
                int months = loan.getTermInMonths();
                double emi = calculateEMI(loan.getAmount(), interestRate, months);

                loan.setInterestRate(interestRate);
                loan.setEmiMonths(months);
                loan.setEmiAmount(emi);
            }

            return repository.save(loan);
        });
    }

    private double calculateEMI(double amount, double rate, int months) {
        double monthlyRate = rate / (12 * 100);
        return (amount * monthlyRate * Math.pow(1 + monthlyRate, months)) /
               (Math.pow(1 + monthlyRate, months) - 1);
    }
}
