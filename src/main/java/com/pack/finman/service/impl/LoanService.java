package com.pack.finman.service.impl;

import com.pack.finman.dto.request.LoanRequest;
import com.pack.finman.entity.Investment;
import com.pack.finman.entity.Loan;
import com.pack.finman.entity.User;
import com.pack.finman.exception.ResourceNotFoundException;
import com.pack.finman.repository.LoanRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public List<Loan> getAll(Long userId, Pageable pageable) {
//    	Pageable pageable2 = PageRequest.of(0, 5);
//    	return loanRepository.findByUserIdOrderByEmiDueDateAsc(userId, pageable2);
//        return loanRepository.findByUserIdOrderByEmiDueDateAsc(userId, pageable);
    	
    	

    	Pageable pageable2 = PageRequest.of(0, 5);

//    	List<Loan> recentLoans =loanRepository
//    	        .findByUserIdOrderByEmiDueDateAsc(userId, pageable2)
//    	        .getContent();
    	
//    	return recentLoans;
    	
    	
    	
    	List<Loan> loanList = new ArrayList<Loan>(); 
    	loanList = loanRepository.findByUserIdOrderByEmiDueDateAsc(userId);

		 return  loanList;
		 
		 
		 
    	
    }

    public Loan getById(Long id, Long userId) {
        return loanRepository.findById(id)
                .filter(l -> l.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + id));
    }

    @Transactional
    public Loan create(LoanRequest request, User user) {
        Loan loan = Loan.builder()
                .user(user)
                .name(request.getName())
                .type(request.getType())
                .totalAmount(request.getTotalAmount())
                .outstandingAmount(request.getOutstandingAmount())
                .emiAmount(request.getEmiAmount())
                .interestRate(request.getInterestRate())
                .emiDueDate(request.getEmiDueDate())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .lenderName(request.getLenderName())
                .build();
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan update(Long id, LoanRequest request, Long userId) {
        Loan loan = getById(id, userId);
        loan.setName(request.getName());
        loan.setType(request.getType());
        loan.setTotalAmount(request.getTotalAmount());
        loan.setOutstandingAmount(request.getOutstandingAmount());
        loan.setEmiAmount(request.getEmiAmount());
        loan.setInterestRate(request.getInterestRate());
        loan.setEmiDueDate(request.getEmiDueDate());
        loan.setStartDate(request.getStartDate());
        loan.setEndDate(request.getEndDate());
        loan.setLenderName(request.getLenderName());
        return loanRepository.save(loan);
    }

    @Transactional
    public void delete(Long id, Long userId) {
        loanRepository.delete(getById(id, userId));
    }
}
