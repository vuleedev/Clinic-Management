package com.hamter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.model.User;
import com.hamter.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/user")
    public OAuth2User getUser(@AuthenticationPrincipal OAuth2User principal) {
        return principal;
    }
	
	@GetMapping
	@PreAuthorize("hasAuthority('CUST')")
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	@PreAuthorize("hasAuthority('CUST')")
	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") Long id) {
		return userService.findById(id);
	}

	@PreAuthorize("hasAuthority('CUST')")
	@PostMapping("/create-user")
	public User createUser(@RequestBody User user) {
		return userService.create(user);
	}

	@PreAuthorize("hasAuthority('CUST')")
	@PutMapping("/{id}")
	public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		user.setId(id);
		return userService.update(user);
	}

	@PreAuthorize("hasAuthority('CUST')")
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userService.delete(id);
	}
}
