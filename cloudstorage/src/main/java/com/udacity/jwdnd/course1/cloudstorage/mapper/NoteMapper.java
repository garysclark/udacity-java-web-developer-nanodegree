package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Mapper
public interface NoteMapper {

	@Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
	@Options(useGeneratedKeys = true, keyProperty = "noteid")
	Integer create(Note note);

	@Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
	Note findById(Integer noteid);

	@Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription}, userid=#{userid} WHERE noteid = #{noteid}")
	void update(Integer noteid, String notetitle, String notedescription, Integer userid);

	@Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
	void delete(Integer noteid);

	@Select("SELECT * FROM NOTES WHERE userid = #{userid}")
	List<Note> findByUserId(Integer userid);

}
