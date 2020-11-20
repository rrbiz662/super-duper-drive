package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
	
	private int fileId;
	private String fileName;
	private String contentType;
	private long fileSize;
	private int userId;
	private byte[] fileData; // or byte[]???
	
	public File(int fileId, String fileName, String contentType, long fileSize, int userId, byte[] fileData) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.userId = userId;
		this.fileData = fileData;
	}
	
	public int getFileId() {
		return fileId;
	}
	
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
}
