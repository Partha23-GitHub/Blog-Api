package com.parthamanna.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parthamanna.blog.config.AppConstants;
import com.parthamanna.blog.entities.Role;
import com.parthamanna.blog.entities.User;
import com.parthamanna.blog.exceptions.ResourceNotFoundException;
import com.parthamanna.blog.payloads.UserDto;
import com.parthamanna.blog.repositories.RoleRepository;
import com.parthamanna.blog.repositories.UserRepository;
import com.parthamanna.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	// creating UserRepo object for performing user service operation
	@Autowired
	private UserRepository userRepo;
	
	//creating model mapper object for conversion of User to UserDto and vice versa
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired private RoleRepository roleRepository;
	
	//for password encoder
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		User savedUser=this.userRepo.save(user);
		
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userDtoId) {
		//finding the user by the userDtoId
		User user =this.userRepo.findById(userDtoId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userDtoId));
		
		// setting all the updated value to the user object
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setProfileHeadline(userDto.getProfileHeadline());
		
		//update the user
		User updatedUser=this.userRepo.save(user);
		
		//return the user
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userDtoId) {
		User user =this.userRepo.findById(userDtoId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userDtoId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users=this.userRepo.findAll(); //find all the users
		// convert all the user to userDto and store in a list
		List<UserDto> dtoUsers=users.stream().map(user ->this.userToDto(user)).collect(Collectors.toList());
		
		return dtoUsers;
	}

	@Override
	public void deleteUser(Integer userDtoId) {
		User user =this.userRepo.findById(userDtoId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userDtoId));
		
		this.userRepo.delete(user);
	}
	
//conversion between Entities and PayLoad using ModelMapper
	public User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		
//		User user=new User();
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setProfileHeadline(userDto.getProfileHeadline());
		
		return user;
	}
	
	public UserDto userToDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setEmail(user.getEmail());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setProfileHeadline(user.getProfileHeadline());
		
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		//encode password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//set roles
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User savedUser = this.userRepo.save(user);
		return this.modelMapper.map(savedUser, UserDto.class);
	}

}
