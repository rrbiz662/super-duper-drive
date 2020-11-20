package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Mapper
public interface CredentialMapper {
	
	@Insert("INSERT INTO CREDENTIALS "
			+ "(url, username, key, password, userid) "
			+ "VALUES "
			+ "(#{url}, #{userName}, #{key}, #{password}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialId")
	int insertCredential(Credential credential);
	
	@Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
	ArrayList<Credential> getCredentials(int userId);

	@Update("UPDATE CREDENTIALS SET "
			+ "url = #{url}, "
			+ "username = #{userName}, "
			+ "key = #{key}, "
			+ "password = #{password} "
			+ "WHERE credentialid = #{credentialId}")
	boolean updateCredential(Credential credential);
	
	@Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
	boolean deleteCredential(int credentialId);
	
}
