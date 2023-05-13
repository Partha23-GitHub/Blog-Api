package com.parthamanna.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.parthamanna.blog.config.AppConstants;
import com.parthamanna.blog.payloads.ApiResponse;
import com.parthamanna.blog.payloads.PostDto;
import com.parthamanna.blog.payloads.PostResponse;
import com.parthamanna.blog.services.FileService;
import com.parthamanna.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	//create post
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	// get all post by category
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>>getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> postsByCategory = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postsByCategory,HttpStatus.OK);
	}
	
	//get posts by user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>>getPostByUser(@PathVariable Integer userId){
		List<PostDto> postsByUser = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);
	}
	
	//get all posts
	@GetMapping("")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue =AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value="sortDirection",defaultValue =AppConstants.SORT_DIR,required = false)String sortDirection){
		
		PostResponse allPosts = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDirection);
		return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
	}
	
	//get a single post by id
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	//update post by id
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto>updatePostbyId(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatePost = this.postService.updatePostById(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatusCode.valueOf(200));
	}
	
	// delete post
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse>deletePostbyId(@PathVariable Integer postId){
		this.postService.deletePostById(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post succesfully deleted",true),HttpStatus.OK);
	}
	@GetMapping("/search")
	public ResponseEntity<List<PostDto>>searchPostByTitle(@RequestParam String keyword){
		List<PostDto> searchPosts = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto>uploadPostImage(@PathVariable Integer postId,@RequestParam MultipartFile image) throws IOException{
		PostDto post = this.postService.getPostById(postId);
		String uploadImageName = this.fileService.uploadImage(path, image);
		post.setImageName(uploadImageName);
		PostDto updatePost = this.postService.updatePostById(post, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//serve the image
	@GetMapping(value="/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void serveImage(@PathVariable String imageName,HttpServletResponse response) throws IOException {
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
}
