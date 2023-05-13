package com.parthamanna.blog.payloads;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private Integer postId;
	@NotEmpty
	@NotBlank
	@NotBlank
	private String title;
	@NotEmpty
	@NotBlank
	@NotBlank
	private String content;
	
	private String imageName;
	@NotEmpty
	@NotBlank
	@NotBlank
	private Date postingDate;
	@NotEmpty
	@NotBlank
	@NotBlank
	private CategoryDto category;
	@NotEmpty
	@NotBlank
	@NotBlank
	private UserDto user;
	
	private List<CommentDto>comments=new ArrayList<>();
}
