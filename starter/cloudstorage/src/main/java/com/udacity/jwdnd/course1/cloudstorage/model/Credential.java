package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {

	private int credentialId;
	private String url;
	private String userName;
	private String key;
	private String password;
	private int userId;
	
	public int getCredentialId() {
		return credentialId;
	}
	
	public void setCredentialId(int credentialId) {
		this.credentialId = credentialId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}	
	
}
