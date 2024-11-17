package com.demo.easylang.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.demo.easylang.entities.Languages;
import com.demo.easylang.entities.LearningMaterials;
import com.demo.easylang.service.LanguageService;

@RestController
@RequestMapping("/api/language")
public class LanguageController {
	
	@Autowired
	private LanguageService languageService;
	@GetMapping
	public List<Languages> getAllLanguages(){
	return	languageService.getAllLanguages();
		
	}
	
	@PostMapping
	public boolean selectLanguage(Integer user_id , Integer language_id,String profiency) {
		System.out.println(user_id+" "+language_id+profiency);
		return languageService.selectLanguage(user_id, language_id,profiency);
	}

	@GetMapping("/getUserLangData")
	public String getLanguageForUser(Integer user_id) {
		
		JSONObject j = languageService.getUserLanguage(user_id);
		System.out.println(j);
		if(j!=null)
		return j.toString();
		
		return null;
		
	}
	
	@GetMapping("/getVocablaryData")
	public   List<Map<Object, Object>> getVocablaryData(Integer user_id, String profiency){
		
		return languageService.getVocablaryData(user_id, profiency);
	}
}
