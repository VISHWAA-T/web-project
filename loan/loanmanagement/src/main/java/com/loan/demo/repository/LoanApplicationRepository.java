package com.loan.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.demo.model.LoanApplication;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByUserId(Long userId);
    List<LoanApplication> findByStatus(String status);
}
