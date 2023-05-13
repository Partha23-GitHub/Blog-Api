package com.parthamanna.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	String resourceName;
	String feildName;
	Integer feildValue;
	public ResourceNotFoundException(String resourceName, String feildName, Integer feildValue) {
		super(String.format("%s not found with %s : %s",resourceName,feildName,feildValue));
		this.resourceName = resourceName;
		this.feildName = feildName;
		this.feildValue = feildValue;
	}
	
	
}
