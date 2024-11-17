package com.demo.easylang.repositry;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.demo.easylang.entities.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findAll();

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.password = :password, u.isTempPassword = :isTempPassword WHERE u.id = :userId")
	int updatePasswordByUserId(@Param("userId") int userId, @Param("password") String password,@Param("isTempPassword") boolean isTempPassword);

	@Query(value = "SELECT validate_user_password(:p_email, :p_password)", nativeQuery = true)
	int validateUserPassword(@Param("p_email") String email, @Param("p_password") String password);

	@Query("SELECT u.id FROM User u WHERE u.email = :email")
	Optional<Integer> getUserIdByEmail(@Param("email") String email);

}
