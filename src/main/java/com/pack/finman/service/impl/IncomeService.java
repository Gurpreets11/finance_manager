package com.pack.finman.service.impl;

import com.pack.finman.dto.request.IncomeRequest;
import com.pack.finman.entity.Income;
import com.pack.finman.entity.Investment;
import com.pack.finman.entity.User;
import com.pack.finman.exception.ResourceNotFoundException;
import com.pack.finman.repository.IncomeRepository;
import com.pack.finman.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    
    @Autowired
    UserRepository userRepository;

    public List<Income> getAll(Long userId, Pageable pageable) {
    	//Pageable pageable2 = PageRequest.of(0, 5);
		/*
		 * List<Income> incomeList = new ArrayList<Income>(); Optional<User> user =
		 * userRepository.findById(userId); if(user.isPresent()) { incomeList =
		 * incomeRepository.findByUserIdOrderByTransactionDateDesc(user.get()); } return
		 * incomeList;
		 */
        //return incomeRepository.findByUserIdOrderByTransactionDateDesc(userId, pageable);
    	
    	

    	//Pageable pageable2 = PageRequest.of(0, 5);

		/*
		 * List<Income> recentIncomes = incomeRepository
		 * .findByUserIdOrderByTransactionDateDesc(userId) .getContent();
		 */
    	
    	
    	
//		List<Income> incomeList = new ArrayList<Income>(); 
//		Optional<User> user = userRepository.findById(userId); 
//		if(user.isPresent()) {
//			 incomeList = incomeRepository.findByUserIdOrderByTransactionDateDesc(user.get().getId());
//		} 
		 
		List<Income> incomeList = new ArrayList<Income>(); 
		 incomeList = incomeRepository.findByUserIdOrderByTransactionDateDesc(userId);

		 return  incomeList;
		 
    	
    	//return recentIncomes;
    	
    }

    public Income getById(Long id, Long userId) {
        return incomeRepository.findById(id)
                .filter(i -> i.getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Income not found: " + id));
    }

    @Transactional
    public Income create(IncomeRequest request, User user) {
        Income income = Income.builder()
                .user(user)
                .title(request.getTitle())
                .category(request.getCategory())
                .amount(request.getAmount())
                .transactionDate(request.getTransactionDate())
                .description(request.getDescription())
                .isRecurring(request.getIsRecurring())
                .categoryEnum(request.getCategoryEnum() != null
                        ? request.getCategoryEnum() : Income.IncomeCategory.OTHER)
                .build();
        return incomeRepository.save(income);
    }

    @Transactional
    public Income update(Long id, IncomeRequest request, Long userId) {
        Income income = getById(id, userId);
        income.setTitle(request.getTitle());
        income.setCategory(request.getCategory());
        income.setAmount(request.getAmount());
        income.setTransactionDate(request.getTransactionDate());
        income.setDescription(request.getDescription());
        income.setIsRecurring(request.getIsRecurring());
        if (request.getCategoryEnum() != null) income.setCategoryEnum(request.getCategoryEnum());
        return incomeRepository.save(income);
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Income income = getById(id, userId);
        incomeRepository.delete(income);
    }
}
