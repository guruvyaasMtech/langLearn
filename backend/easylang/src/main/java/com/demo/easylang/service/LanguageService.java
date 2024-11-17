package com.demo.easylang.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.demo.easylang.entities.Languages;
import com.demo.easylang.entities.LearningMaterials;
import com.demo.easylang.entities.UserLanguage;
import com.demo.easylang.repositry.LanguageRepositry;
import com.demo.easylang.repositry.UserLanguagesRepository;

@Service
public class LanguageService {

	@Autowired
	private LanguageRepositry languageRepositry;

	@Autowired
	private UserLanguagesRepository userlanguageRepository;

	public List<Languages> getAllLanguages() {

		return languageRepositry.findAll();

	}

	public boolean selectLanguage(int user_id, int language_id, String profiency) {

		return languageRepositry.insertLanguageRecord(user_id, language_id, profiency);

	}

	public JSONObject getUserLanguage(int user_id) {
		System.out.println(user_id);

		Object i = userlanguageRepository.getUserLanguagesByUserIdObject(user_id);
		if (i != null) {
			JSONObject json = new JSONObject();
			UserLanguage u = userlanguageRepository.getUserLanguagesByUserId(user_id);
			Optional<Languages> l = languageRepositry.findById(u.getLanguage());
			Languages li = null;
			if (l.isPresent()) {
				li = l.get();

				json.put("language_id", u.getLanguage());
				json.put("language", li.getName());
				json.put("profiency", u.getProficiency());

				return json;
			} else {
				return null;
			}
		}
		return null;
	}

	public List<Map<Object, Object>> getVocablaryData(Integer user_id, String profiency) {
		System.out.println(user_id + profiency);
		List<Map<Object, Object>> m = languageRepositry.getVocablaryData1(user_id,profiency);
System.out.println(m);
		return m;
	}
}
