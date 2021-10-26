package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Service
public class FileService {

	private FileMapper fileMapper;

	public FileService(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}

	public Integer addFile(Integer userId, MultipartFile file) {
		byte[] data = null;
		
		try {
			data = file.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		
		File newFile = new File(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), userId, data);
		
		return fileMapper.create(newFile);
	}

	public List<File> getFilesByUserId(Integer userId) {
		return fileMapper.findByUserId(userId);
	}

	public File getFileByFileId(Integer fileId) {
		return fileMapper.findById(fileId);
	}

	public int deleteFile(File file) {
		return fileMapper.delete(file);
	}

}
