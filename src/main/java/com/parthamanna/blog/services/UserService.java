package com.parthamanna.blog.services;

import java.util.List;

import com.parthamanna.blog.payloads.UserDto;

public interface UserService {
	//for adding a new User into DB
	public UserDto createUser(UserDto userDto);
	
	//for updating a new User into DB
	public UserDto updateUser(UserDto userDto,Integer userId);
	
	//get User by Id
	public UserDto getUserById(Integer userDtoId);
	
	//get all Users
	public List<UserDto> getAllUsers();
	
	//Delete a user
	public void deleteUser(Integer userDtoId);
	
}
