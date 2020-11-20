package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Service
public class CredentialService {
	
	private CredentialMapper credentialMapper;
	
	public CredentialService(CredentialMapper credentialMapper) {
		this.credentialMapper = credentialMapper;
	}
	
	public int insertCredential(Credential credential) {
		return credentialMapper.insertCredential(credential);
	}
	
	public ArrayList<Credential> getCredentials(int userId) {
		return credentialMapper.getCredentials(userId);
	}
	
	public boolean updateCredential(Credential credential) {
		return credentialMapper.updateCredential(credential);
	}
	
	public boolean deleteCredential(int credentialId) {
		return credentialMapper.deleteCredential(credentialId);
	}

}
