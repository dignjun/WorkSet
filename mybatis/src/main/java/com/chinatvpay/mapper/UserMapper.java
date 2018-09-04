package com.chinatvpay.mapper;

import java.util.List;

import com.chinatvpay.bean.User;
import com.chinatvpay.bean.User_tb;

public interface UserMapper {

	public User findUserById(int id);
	
	public void insertUser_tb(List<User_tb> list);
	
}
