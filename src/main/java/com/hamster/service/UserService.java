package com.hamster.service;

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
import com.hamster.dto.UserDTO;
import com.hamster.exception.DataNotFoundException;
import com.hamster.interfaceService.IUserService;
import com.hamster.model.Roles;
import com.hamster.model.User;
import com.hamster.repository.RoleRepository;
import com.hamster.repository.UserRepository;

@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository,
	                   BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
	                   JwtTokenUtil jwtTokenUtil) {
	    this.userRepository = userRepository;
	    this.roleRepository = roleRepository;
	    this.passwordEncoder = passwordEncoder;
	    this.authenticationManager = authenticationManager;
	    this.jwtTokenUtil = jwtTokenUtil;
	}
	
	private AuthenticationManager authenticationManager;
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	public User register(UserDTO userDTO) throws Exception {
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
				.positionId(userDTO.getPositionId())
				.image(userDTO.getImage())
				.createdAt(new Date())
				.updatedAt(new Date())
				.build();
		Roles role = roleRepository.findByName(userDTO.getRoleName())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
		newUser.setRole(role);
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
