package com.demo.easylang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.easylang.entities.User;
import com.demo.easylang.repositry.UserRepository;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUser(Integer userId) {
		return userRepository.findById(userId);
	}

	public boolean createUser(String password, String email, String full_name, String mobile) {
		try {
			User u = new User();
			u.setEmail(email);
			u.setFull_name(full_name);
			u.setMobile(mobile);
			u.setPassword(password);
			u.setRole_id(1);
			userRepository.save(u);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean changePassword(String password, Integer user_id, boolean isTempPassword) {
		int rowsAffected = userRepository.updatePasswordByUserId(user_id, password,isTempPassword);
		if (rowsAffected > 0) {
			return true;
		}
		return false;
	}

	public User validatePassword(String password, String email) {

		Integer i = userRepository.validateUserPassword(email, password);
		System.out.println(i);
		if (i > 0) {
			return userRepository.findById(i).get();
		}
		return null;
	}

	public String resetPassword(String email) {

		 Optional<Integer> i = userRepository.getUserIdByEmail(email);
		if (i.isPresent()) {
			String tempPassword = emailService.generateTemporaryPassword();
			changePassword(tempPassword, i.get(),true);
			try {
				emailService.sendTemporaryPassword(email, tempPassword);
				return "success";
			} catch (Exception e) {
				return "failure";

			}
		} else {
			return "No User";
		}
	}
}