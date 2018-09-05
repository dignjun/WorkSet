package com.chinatvpay.mapper;

import java.util.List;

import com.chinatvpay.bean.User_tb;

public interface User_tbMapper {

	public User_tb findUser_tbByName(String name);
	
	public List<User_tb> findUser_tbByNameLike(/*@Param(value = "name")*/String name);
	
}
