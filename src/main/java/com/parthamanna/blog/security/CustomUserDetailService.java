package com.parthamanna.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parthamanna.blog.entities.User;
import com.parthamanna.blog.exceptions.ResourceNotFoundException;
import com.parthamanna.blog.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Loading user from DB by userName(email)
		User user = this.userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User", "email "+email, 0));
		
		return user;
	}
	
}
