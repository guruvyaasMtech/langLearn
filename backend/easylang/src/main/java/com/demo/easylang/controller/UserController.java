package com.demo.easylang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.demo.easylang.entities.User;
import com.demo.easylang.repositry.UserRepository;
import com.demo.easylang.service.UserService;

import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Optional;
import org.json.JSONObject;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public List<User> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return users;
	}

	@PostMapping("/create")
	public boolean createUser(@RequestBody String JSONFormdata) {
		try {
			System.out.println(JSONFormdata);
			JSONObject jsonObject = new JSONObject(JSONFormdata);
			System.out.println(jsonObject);
			String password = jsonObject.getString("password");
			String email = jsonObject.getString("email");
			String full_name = jsonObject.getString("full_name");
			String mobile = jsonObject.getString("mobile");
			return userService.createUser(password, email, full_name, mobile);
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping("/updatePassword")
	public boolean updatePassword(@RequestBody String JSONFormdata) {
		JSONObject jsonObject = new JSONObject(JSONFormdata);

		Integer user_id = jsonObject.getInt("id");
		String password = jsonObject.getString("password");
		System.out.println(user_id +"aff"+password);
		return userService.changePassword(password, user_id,false);
	}

	@PostMapping("/validatePassword")
	public User validatePassword(@RequestBody String JSONFormdata) {
		System.out.println(JSONFormdata);
		JSONObject jsonObject = new JSONObject(JSONFormdata);

		String password = jsonObject.getString("password");
		String email = jsonObject.getString("email");
		return userService.validatePassword(password, email);
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestBody String JSONFormdata) {
		System.out.println(JSONFormdata);
		JSONObject jsonObject = new JSONObject(JSONFormdata);

		String email = jsonObject.getString("email");
		return userService.resetPassword(email);

	}
}
