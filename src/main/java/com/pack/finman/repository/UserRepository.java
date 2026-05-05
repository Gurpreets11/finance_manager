package com.pack.finman.repository;

import com.pack.finman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);
    
	/*
	 * @Query("SELECT u FROM User u WHERE u.email = :email") boolean
	 * existsByEmail(String email);
	 */
}
