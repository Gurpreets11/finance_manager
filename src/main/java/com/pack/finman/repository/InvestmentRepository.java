package com.pack.finman.repository;

import com.pack.finman.entity.Expense;
import com.pack.finman.entity.Investment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

	
	 @Query(value = """
		        SELECT * FROM investments
		        WHERE user_id = :userId
		        ORDER BY current_value DESC
		        LIMIT 3
		        """, nativeQuery = true)
		    List<Investment> findTop3Investments(Long userId);
	 
	 
    //Page<Investment> findByUserIdOrderByCurrentValueDesc(Long userId, Pageable pageable);
    
    @Query(value = """
	        SELECT * FROM investments
	        WHERE user_id = :userId
	        ORDER BY current_value DESC
	        """, nativeQuery = true)
 List<Investment> findByUserIdOrderByCurrentValueDesc( Long userId);
 
    
//    List<Investment> findByUserIdOrderByCurrentValueDesc(Long userId, Pageable pageable);

    List<Investment> findTop3ByUserIdOrderByCurrentValueDesc(Long userId);

    @Query("SELECT SUM(i.currentValue) FROM Investment i WHERE i.user.id = :userId")
    BigDecimal sumCurrentValueByUserId(Long userId);

    @Query("SELECT SUM(i.investedAmount) FROM Investment i WHERE i.user.id = :userId")
    BigDecimal sumInvestedAmountByUserId(Long userId);
}
