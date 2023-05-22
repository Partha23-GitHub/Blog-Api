package com.parthamanna.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.parthamanna.blog.config.AppConstants;
import com.parthamanna.blog.entities.Role;
import com.parthamanna.blog.repositories.RoleRepository;

@SpringBootApplication
public class BlogappApiApplication implements CommandLineRunner{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//for inserting role while application is running for the 1st time
	@Autowired private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogappApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println("password: "+ passwordEncoder.encode("Partha123456"));
		try {
			Role adminRole=new Role();
			adminRole.setId(AppConstants.ADMIN_USER);
			adminRole.setName("ROLE_ADMIN");
			
			Role normalRole=new Role();
			normalRole.setId(AppConstants.NORMAL_USER);
			normalRole.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(adminRole,normalRole);
			
			List<Role> savedRoles = this.roleRepository.saveAll(roles);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
