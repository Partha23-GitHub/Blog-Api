package com.parthamanna.blog.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.parthamanna.blog.payloads.UserDto;
import com.parthamanna.blog.services.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//create a user
	@PostMapping("/")
	public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
		//create the user
		UserDto createdUserDto=this.userService.createUser(userDto);
		// return the user with httpStatus code
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	
	//update a user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId){
		UserDto updatedUser= this.userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
	}
	
	//delete a user
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> ueleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		
		return new ResponseEntity<>(Map.of("message","user deleted successfully"),HttpStatus.OK);
	}
	
	//get a user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto>getAUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	//get All the User
	@GetMapping("/all")
	public ResponseEntity<List<UserDto>>getAllUser(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
}
