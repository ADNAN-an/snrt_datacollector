package com.snrt.datacollector.controllers;

import com.snrt.datacollector.DTO.CreateAdminRequest;
import com.snrt.datacollector.exceptions.UserAlreadyExistsException;
import com.snrt.datacollector.models.User;
import com.snrt.datacollector.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/superadmin")
public class SuperadminController {

	@Autowired
	private UserService userService;

	@PostMapping("/add-user")
	public ResponseEntity<?> createAdmin(@RequestBody CreateAdminRequest request) {
		try {
			User user = userService.createAdmin(request.getEmail(), request.getPassword(), request.getRole());
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@DeleteMapping("/delete-user/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
		boolean isDeleted = userService.deleteUserById(id);
		if (isDeleted) {
			return ResponseEntity.ok("User with ID " + id + " was successfully deleted.");
		} else {
			return ResponseEntity.status(404).body("User with ID " + id + " does not exist.");
		}
	}

}
