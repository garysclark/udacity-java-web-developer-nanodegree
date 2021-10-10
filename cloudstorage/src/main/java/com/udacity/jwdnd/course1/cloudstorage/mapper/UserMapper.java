package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO USERS (username, salt, password, firstName, lastName) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
	@Options(useGeneratedKeys = true, keyProperty = "userId")
	Integer insert(User user);

	@Select("SELECT * FROM USERS WHERE userid = #{userid}")
	User findById(Integer userid);

	@Select("SELECT * FROM USERS WHERE username = #{username}")
	User findByUsername(String username);

}
