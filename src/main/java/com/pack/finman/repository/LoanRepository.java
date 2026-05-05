package com.pack.finman.repository;

import com.pack.finman.entity.Investment;
import com.pack.finman.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

	
	@Query("""
	        SELECT l
	        FROM Loan l
	        WHERE l.user.id = :userId
	        AND l.emiDueDate BETWEEN :startDate AND :endDate
	        ORDER BY l.emiDueDate ASC
	        """)
	    List<Loan> findUpcomingPayments(
	            Long userId,
	            LocalDate startDate,
	            LocalDate endDate);
	
	
    //Page<Loan> findByUserIdOrderByEmiDueDateAsc(Long userId, Pageable pageable);
    
    
	/*
	 * @Query(value = """ SELECT l FROM Loan l WHERE l.user.id = :userId ORDER BY
	 * l.emiDueDate ASC """, nativeQuery = true)
	 */
    
	 @Query(value = """
		        SELECT * FROM loans
		        WHERE user_id = :userId
		        ORDER BY emi_due_date DESC
		        """, nativeQuery = true)
    	List<Loan> findByUserIdOrderByEmiDueDateAsc( Long userId);
 
    
    
//    List<Loan> findByUserIdOrderByEmiDueDateAsc(Long userId, Pageable pageable);

    // Loans with upcoming EMIs in the next N days
    List<Loan> findByUserIdAndEmiDueDateBetweenOrderByEmiDueDateAsc(
            Long userId, LocalDate from, LocalDate to);

    @Query("SELECT SUM(l.outstandingAmount) FROM Loan l WHERE l.user.id = :userId")
    BigDecimal sumOutstandingByUserId(Long userId);

    @Query("SELECT SUM(l.emiAmount) FROM Loan l WHERE l.user.id = :userId")
    BigDecimal sumMonthlyEmiByUserId(Long userId);
}
