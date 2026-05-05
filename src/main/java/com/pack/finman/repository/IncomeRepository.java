package com.pack.finman.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pack.finman.entity.Income;
import com.pack.finman.entity.User;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

	
	 @Query(value = """
		        SELECT * FROM incomes
		        WHERE user_id = :userId
		        ORDER BY transaction_date DESC
		        LIMIT 5
		        """, nativeQuery = true)
		    List<Income> findRecentTop5(Long userId);

		     
		    
    //Page<Income> findByUserIdOrderByTransactionDateDesc(Long userId, Pageable pageable);
    
     
	/*
	 * @Query(value = """ SELECT * FROM income WHERE user_id = :userId ORDER BY
	 * transaction_date DESC """, countQuery = """ SELECT COUNT(*) FROM income WHERE
	 * user_id = :userId """, nativeQuery = true) Page<Income>
	 * findByUserIdOrderByTransactionDateDesc(@Param("userId") Long userId, Pageable
	 * pageable);
	 */
	 
	 @Query(value = """
		        SELECT * FROM incomes
		        WHERE user_id = :userId
		        ORDER BY transaction_date DESC
		        """, nativeQuery = true)
	 List<Income> findByUserIdOrderByTransactionDateDesc( Long userId);
	 
	 
	//@Query("SELECT a FROM Income a WHERE a.user = :user ORDER BY a.transactionDate DESC")
//    List<Income> findByUserIdOrderByTransactionDateDesc(@Param("user") User user);
    
    @Query(value = "SELECT * FROM incomes WHERE user_id = :userId ORDER BY transaction_date DESC LIMIT 5", nativeQuery = true)
    List<Income> findTop5ByUserIdOrderByTransactionDateDesc(Long userId);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.user.id = :userId " +
           "AND i.transactionDate BETWEEN :start AND :end")
    BigDecimal sumAmountByUserIdAndDateRange(Long userId, LocalDate start, LocalDate end);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.user.id = :userId")
    BigDecimal sumTotalByUserId(Long userId);
}
