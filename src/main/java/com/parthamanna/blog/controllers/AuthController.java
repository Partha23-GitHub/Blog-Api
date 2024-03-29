package com.parthamanna.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parthamanna.blog.entities.User;
import com.parthamanna.blog.exceptions.ApiException;
import com.parthamanna.blog.payloads.JwtAuthRequest;
import com.parthamanna.blog.payloads.JwtAuthResponse;
import com.parthamanna.blog.payloads.UserDto;
import com.parthamanna.blog.security.JwtTokenHelper;
import com.parthamanna.blog.services.UserService;
//import com.parthamanna.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	@Autowired ModelMapper modelMapper;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getEmail(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.modelMapper.map((User) userDetails, UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}
	
	
	
	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			throw new ApiException("Invalid username or password !!");
		}

	}
	
	//register user
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) throws Exception {
		UserDto registerUser = this.userService.registerUser(userDto);
		
		return new ResponseEntity<UserDto>(registerUser, HttpStatus.CREATED);
	}
}
