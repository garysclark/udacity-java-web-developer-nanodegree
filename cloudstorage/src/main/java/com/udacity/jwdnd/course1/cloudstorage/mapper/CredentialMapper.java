package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Mapper
public interface CredentialMapper {

	@Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer create(Credential credential);

	@Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{id}")
	Credential findById(Integer id);

	@Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
	List<Credential> findByUserId(Integer userId);

	@Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password}, userid=#{userid} WHERE credentialid = #{id}")
	Integer update(Credential credential);

	@Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id}")
	Integer delete(Credential credential);

}
