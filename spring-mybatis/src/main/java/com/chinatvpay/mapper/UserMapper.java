package com.chinatvpay.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.chinatvpay.bean.User;

public interface UserMapper {
	
	@Select("SELECT * FROM student WHERE id = #{userId}")
	User getUser(@Param("userId") String userId);
	
}
