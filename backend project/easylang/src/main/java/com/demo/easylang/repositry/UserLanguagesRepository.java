package com.demo.easylang.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.easylang.entities.Languages;
import com.demo.easylang.entities.UserLanguage;

public interface UserLanguagesRepository  extends JpaRepository<UserLanguage, Integer>  {

	@Query(value = "SELECT ul FROM UserLanguages ul WHERE ul.user_id = :userId and active=true", nativeQuery = true)
	Object getUserLanguagesByUserIdObject(@Param("userId") Integer userId);

	

	@Query(value = "SELECT ul.id, ul.user_id, ul.language, ul.proficiency, ul.active FROM UserLanguages ul WHERE ul.user_id = :userId and active=true", nativeQuery = true)
	UserLanguage getUserLanguagesByUserId(@Param("userId") Integer userId);

}
