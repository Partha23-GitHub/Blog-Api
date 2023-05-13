package com.parthamanna.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.parthamanna.blog.repositories.UserRepository;

@SpringBootTest
class BlogappApiApplicationTests {
	@Autowired
	private UserRepository userRepo;
	
	@Test
	void contextLoads() {
	}
	@Test
	public void userRepoTest() {
		System.out.println(this.userRepo.getClass().getPackageName());
	}
}
