package com.parthamanna.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.parthamanna.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//extracting file name
		String originalFilename = file.getOriginalFilename();
		
		//generating a random name for file
		String uniqueFileNameWithExtension=UUID.randomUUID().toString().concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
		//making full path
		String fullPath=path+File.separator+uniqueFileNameWithExtension;
		
		//create folder id folder is not exist
		File f=new File(path);
		if(!f.exists())
			f.mkdir();
		
		//copy file
		Files.copy(file.getInputStream(),Paths.get(fullPath));
		
		return uniqueFileNameWithExtension;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath=path + File.separator + fileName;
		InputStream is=new FileInputStream(fullPath);
		return is;
	}

}
