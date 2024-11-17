package com.demo.easylang.repositry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.demo.easylang.entities.Languages;
import com.demo.easylang.entities.LearningMaterials;
import com.demo.easylang.entities.User;

import jakarta.transaction.Transactional;

public interface LanguageRepositry extends JpaRepository<Languages, Integer> {

	List<Languages> findAll();

	@Query(value = "SELECT insert_user_language_and_materials(:user_id,:language_id,:p_proficiency)", nativeQuery = true)
	boolean insertLanguageRecord(@Param("user_id") int user_id, @Param("language_id") int language_id,
			@Param("p_proficiency") String p_proficiency);

	@Query(value = "select * from learningmaterials ul where language_id= (select language from userlanguages where user_id=:user_id and active=true) and ul.proficiency=:profiency", nativeQuery = true)
	List<Map<Object, Object>> getVocablaryData1(@Param("user_id") Integer user_id,@Param("profiency") String profiency);
}
