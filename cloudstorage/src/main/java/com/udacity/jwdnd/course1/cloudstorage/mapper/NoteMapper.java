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

	@Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{title}, #{description}, #{userid})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer create(Note note);

	@Select("SELECT * FROM NOTES WHERE noteid = #{id}")
	Note findById(Integer noteid);

	@Update("UPDATE NOTES SET notetitle=#{title}, notedescription=#{description}, userid=#{userid} WHERE noteid = #{id}")
	Integer update(Note note);

	@Select("SELECT * FROM NOTES WHERE userid = #{userid}")
	List<Note> findByUserId(Integer userid);

	@Delete("DELETE FROM NOTES WHERE noteid = #{id}")
	Integer delete(Note note);

}
