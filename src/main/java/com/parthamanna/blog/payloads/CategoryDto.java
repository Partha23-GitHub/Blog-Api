package com.parthamanna.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
	private Integer categoryId;
	@NotNull
	@NotBlank
	@NotEmpty
	private String categoryTitle;
	@NotNull
	@NotBlank
	@NotEmpty
	private String categoryDescription;
}
