package com.chinatvpay;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinatvpay.bean.User;
import com.chinatvpay.mapper.UserMapper;

/**
 * 测试
 * @author DINGJUN
 *
 */
public class MybatisTest {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		UserMapper mapper = context.getBean(UserMapper.class);
		User user = mapper.getUser("1");
		System.out.println(user);
	}
	
}
