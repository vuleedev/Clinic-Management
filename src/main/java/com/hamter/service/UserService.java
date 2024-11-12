package com.hamter.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hamster.component.JwtTokenUtil;
import com.hamster.interfaceService.IUserService;
import com.hamter.dto.UserDTO;
import com.hamter.model.Roles;
import com.hamter.model.User;
import com.hamter.repository.RoleRepository;
import com.hamter.repository.UserRepository;

@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private AuthenticationManager authenticationManager;
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	public User register(UserDTO userDTO, Roles role) throws Exception {
		String email = userDTO.getEmail();
		
		// Check email
		if(userRepository.existsByEmail(email)) {
			throw new Exception("Email already exist");
		}
		
		// convert from UserDTO => User
		User newUser = User.builder().firstName(userDTO.getFirstName())
				.lastName(userDTO.getLastName())
				.email(userDTO.getEmail())
				.password(userDTO.getPassword())
				.address(userDTO.getAddress())
				.gender(userDTO.getGender())
				.phoneNumber(userDTO.getPhoneNumber())
				.roles(Set.of(role))
				.positionId(userDTO.getPositionId())
				.image(userDTO.getImage())
				.createdAt(new Date())
				.updatedAt(new Date())
				.build();
		return userRepository.save(newUser);
	}


	@Override
	public String login(String email, String password) throws Exception {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if(optionalUser.isEmpty()) {
			throw new Exception("Invalid email / password");
		}
		User existingUser = optionalUser.get();
		// check password
		if(!passwordEncoder.matches(password, existingUser.getPassword())) {
			throw new BadCredentialsException("Wrong email or password");
		}
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password, existingUser.getAuthorities());
		// Authenticate with Java Spring Security
		authenticationManager.authenticate(authenticationToken);
		return jwtTokenUtil.generateToken(existingUser);
	}


	@Override
	public String logout(String token) {
		// Thêm token vào blacklist để hủy token
		jwtTokenUtil.invalidateToken(token);
		return "Logout successfully";
	}
}
