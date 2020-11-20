package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

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
	
	public int insertFile(File file) {
		return fileMapper.insertFile(file);
	}
	
	public ArrayList<File> getFiles(int userId){
		return fileMapper.getFiles(userId);
	}
	
	public File getFile(int fileId) {
		return fileMapper.getFile(fileId);
	}
	
	public boolean fileExists(String fileName) {
		return fileMapper.fileExists(fileName);
	}
	
	public boolean updateFile(File file) {
		return fileMapper.updateFile(file);
	}
	
	public boolean deleteFile(int fileId) {
		return fileMapper.deleteFile(fileId);
	}
	
}
