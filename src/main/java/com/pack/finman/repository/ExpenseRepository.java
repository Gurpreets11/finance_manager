package com.pack.finman.repository;

import com.pack.finman.entity.Expense;
import com.pack.finman.entity.Income;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	 @Query(value = """
		        SELECT * FROM expenses
		        WHERE user_id = :userId
		        ORDER BY transaction_date DESC
		        LIMIT 5
		        """, nativeQuery = true)
		    List<Expense> findRecentTop5(Long userId);

		    
		    
   // Page<Expense> findByUserIdOrderByTransactionDateDesc(Long userId, Pageable pageable);
    
    @Query(value = """
	        SELECT * FROM expenses
	        WHERE user_id = :userId
	        ORDER BY transaction_date DESC
	        """, nativeQuery = true)
 List<Expense> findByUserIdOrderByTransactionDateDesc( Long userId);
 
    
//    List<Expense> findByUserIdOrderByTransactionDateDesc(Long userId, Pageable pageable);

    List<Expense> findTop5ByUserIdOrderByTransactionDateDesc(Long userId);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId " +
           "AND e.transactionDate BETWEEN :start AND :end")
    BigDecimal sumAmountByUserIdAndDateRange(Long userId, LocalDate start, LocalDate end);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId")
    BigDecimal sumTotalByUserId(Long userId);
}
