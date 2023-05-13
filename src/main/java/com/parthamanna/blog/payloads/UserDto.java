package com.parthamanna.blog.payloads;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Integer id;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String name;
	
	@Email
	@NotBlank
	@NotEmpty
	private String email;
	
	@NotNull
	@NotBlank
	@Size(min=5,message="Password must be atleast 5 and maximum 30 character long",max=30)
	//@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{3,30}$")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@Size(max=250,message="Headline should be within 250 character")
	private String profileHeadline;
}
