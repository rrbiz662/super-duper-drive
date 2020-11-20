package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Mapper
public interface FileMapper {
	
	@Insert("INSERT INTO FILES "
			+ "(filename, contenttype, filesize, userid, filedata) "
			+ "VALUES "
			+ "(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int insertFile(File file);
	
	@Select("SELECT * FROM FILES WHERE userid = #{userId}")
	ArrayList<File> getFiles(int userId);
	
	@Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
	File getFile(int fileId);
	
	@Select("SELECT EXISTS(SELECT 1 FROM FILES WHERE filename=#{fileName})")
	boolean fileExists(String fileName);

	@Update("UPDATE FILES SET "
			+ "filename = #{fileName}, "
			+ "contenttype = #{contentType}, "
			+ "filesize = #{fileSize}, "
			+ "filedata = #{fileData} "
			+ "WHERE fileid = #{fileId}")
	boolean updateFile(File file);
	
	@Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
	boolean deleteFile(int fileId);
	
}
