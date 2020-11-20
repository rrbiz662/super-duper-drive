package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Mapper
public interface NoteMapper {
	
	@Insert("INSERT INTO NOTES"
			+ "(notetitle, notedescription, userid) "
			+ "VALUES"
			+ "(#{noteTitle}, #{noteDescription}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "noteId")
	int insertNote(Note note);
	
	@Select("SELECT * FROM NOTES WHERE userid = #{userId}")
	ArrayList<Note> getNotes(int userId);

	@Update("UPDATE NOTES SET "
			+ "notetitle = #{noteTitle}, "
			+ "notedescription = #{noteDescription} "
			+ "WHERE noteid = #{noteId}")
	boolean updateNote(Note note);
	
	@Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
	boolean deleteNote(int noteId);
	
}
