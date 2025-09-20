package com.loan.demo.service;

import com.loan.demo.model.Repayment;
import com.loan.demo.repository.RepaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepaymentService {

    @Autowired
    private RepaymentRepository repaymentRepository;

    public List<Repayment> getRepaymentsByLoanId(Long loanId) {
        return repaymentRepository.findByLoanId(loanId);
    }

    public Repayment addRepayment(Repayment repayment) {
        // You can add validation or business logic here
        return repaymentRepository.save(repayment);
    }

    public Optional<Repayment> getRepaymentById(Long id) {
        return repaymentRepository.findById(id);
    }

    public void deleteRepayment(Long id) {
        repaymentRepository.deleteById(id);
    }
}

