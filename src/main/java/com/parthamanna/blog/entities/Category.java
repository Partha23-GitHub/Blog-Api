package com.parthamanna.blog.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categories")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	@Column(name="title",nullable=false)
	private String categoryTitle;
	@Column(name="description",nullable=false)
	private String categoryDescription;
	
	@OneToMany(mappedBy="category",cascade = CascadeType.ALL)	//mapped to Posts category 
	private List<Posts> posts=new ArrayList<>();

}
