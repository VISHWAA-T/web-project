package com.loan.demo.service;

import com.loan.demo.model.LoanApplication;
import com.loan.demo.model.Repayment;
import com.loan.demo.repository.LoanApplicationRepository;
import com.loan.demo.repository.RepaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private LoanApplicationRepository loanRepo;

    @Autowired
    private RepaymentRepository repaymentRepo;

    // Loan summary: total loans, approved, rejected, pending counts
    public Map<String, Object> getLoanSummary() {
        List<LoanApplication> loans = loanRepo.findAll();

        long totalLoans = loans.size();
        long approved = loans.stream().filter(l -> "APPROVED".equalsIgnoreCase(l.getStatus())).count();
        long rejected = loans.stream().filter(l -> "REJECTED".equalsIgnoreCase(l.getStatus())).count();
        long pending = loans.stream().filter(l -> "PENDING".equalsIgnoreCase(l.getStatus())).count();

        double totalAmount = loans.stream().mapToDouble(LoanApplication::getAmount).sum();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalLoans", totalLoans);
        summary.put("approved", approved);
        summary.put("rejected", rejected);
        summary.put("pending", pending);
        summary.put("totalAmount", totalAmount);

        return summary;
    }

    // EMI collection report: total collected amount grouped by month (current year)
    public Map<String, Double> getEMICollectionReport() {
        List<Repayment> repayments = repaymentRepo.findAll();

        Map<String, Double> monthlyCollections = repayments.stream()
            .filter(r -> r.getPaymentDate().getYear() == LocalDate.now().getYear())
            .collect(Collectors.groupingBy(
                r -> r.getPaymentDate().getMonth().toString(),
                Collectors.summingDouble(Repayment::getAmountPaid)
            ));

        return monthlyCollections;
    }

    // Delinquency report: loans overdue > certain days (e.g., 30 days) without recent payment
    public List<LoanApplication> getOverdueLoans() {
        List<LoanApplication> approvedLoans = loanRepo.findByStatus("APPROVED");
        List<LoanApplication> overdueLoans = new ArrayList<>();

        LocalDate cutoffDate = LocalDate.now().minusDays(30);

        for (LoanApplication loan : approvedLoans) {
            List<Repayment> repayments = repaymentRepo.findByLoanId(loan.getId());
            Optional<LocalDate> lastPaymentDate = repayments.stream()
                .map(Repayment::getPaymentDate)
                .max(LocalDate::compareTo);

            if (lastPaymentDate.isEmpty() || lastPaymentDate.get().isBefore(cutoffDate)) {
                overdueLoans.add(loan);
            }
        }

        return overdueLoans;
    }
}
