package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Mapper
public interface FileMapper {

	@Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{name}, #{contentType}, #{size}, #{userId}, #{data})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer create(File file);

	@Select("SELECT * FROM FILES WHERE userid = #{userId}")
	List<File> findByUserId(Integer userId);

	@Select("SELECT * FROM FILES WHERE fileId = #{id}")
	File findById(Integer id);

	@Update("UPDATE FILES SET filename=#{name}, contenttype=#{contentType}, filesize=#{size}, userid=#{userId}, filedata=#{data} WHERE fileId = #{id}")
	void update(Integer id, String name, String contentType, String size, Integer userId, byte[] data);

	@Delete("DELETE FROM FILES WHERE fileId = #{id}")
	void delete(File file);

}
