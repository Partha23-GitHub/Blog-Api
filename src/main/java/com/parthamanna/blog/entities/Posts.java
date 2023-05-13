package com.parthamanna.blog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Posts { 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false,length=10000)
	private String content;
	
	private String imageName;
	
	private Date postingDate;
	
	@ManyToOne
	@JoinColumn(name="category_id",nullable = false)
	private Category category;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<Comment>comments=new ArrayList<>();
}
