package com.parthamanna.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.parthamanna.blog.entities.Category;
import com.parthamanna.blog.entities.Posts;
import com.parthamanna.blog.entities.User;
import com.parthamanna.blog.exceptions.ResourceNotFoundException;
import com.parthamanna.blog.payloads.PostDto;
import com.parthamanna.blog.payloads.PostResponse;
import com.parthamanna.blog.repositories.CategotyRepository;
import com.parthamanna.blog.repositories.PostRepository;
import com.parthamanna.blog.repositories.UserRepository;
import com.parthamanna.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategotyRepository categoryRepository;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user Id", userId));
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		Posts post = this.modelMapper.map(postDto, Posts.class);
		post.setImageName("default.png");
		post.setPostingDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Posts savedPost = this.postRepository.save(post);
		
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePostById(PostDto postDto, Integer postId) {
		Posts post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		this.postRepository.save(post);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePostById(Integer postId) {
		Posts post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		this.postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDirection) {
		//for pagination
		Sort sort=sortDirection.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Posts>pagePost= this.postRepository.findAll(p);
		List<Posts> allPost = pagePost.getContent();
		
		List<PostDto> allPostDtos = allPost.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		//setting post response for returning
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContents(allPostDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements((int)pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Posts post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId){
		Category category=this.categoryRepository.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Posts>posts=this.postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
	
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		List<Posts> postsByUser = this.postRepository.findByUser(user);
		List<PostDto> postsDtoByUser = postsByUser.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDtoByUser;
	}

	@Override
	public List<PostDto> searchPosts(String keykord) {
		List<Posts> postsByTitle = this.postRepository.findByTitleContains(keykord);
		List<PostDto> postsDtoByTitle = postsByTitle.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDtoByTitle;
	}

}
