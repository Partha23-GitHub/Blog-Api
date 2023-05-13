package com.parthamanna.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	private Integer pageNumber;
	private Integer pageSize;
	private List<PostDto>contents;
	private Integer totalElements;
	private Integer totalPages;
	private boolean isLastPage;
}
