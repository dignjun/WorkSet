package com.chinatvpay;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.chinatvpay.bean.User_tb;

/**
 * 单元测试
 * @author DINGJUN
 *
 */
public class MybatisTest {
	
	private static SqlSessionFactory sqlSessionFactory;

	@Before
	public void Init(){
		try {
			InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			System.err.println("读取配置文件异常"+e.getMessage());
		}
	}
	
	@Test
	public void selectTest(){
		SqlSession session = sqlSessionFactory.openSession();
		Object user = session.selectOne("com.chinatvpay.mapper.UserMapper.findUserById", "1");
		System.out.println(user);
	}
	
	@Test
	public void insertTest(){
		SqlSession session = sqlSessionFactory.openSession();
		List<User_tb> list = new ArrayList<User_tb>();
		User_tb user1 = new User_tb(1,"张三","123456");
		User_tb user2 = new User_tb(1,"李四","654321");
		User_tb user3 = new User_tb(1,"王武","=-=-=-");
		list.add(user3);
		list.add(user2);
		list.add(user1);
		session.insert("com.chinatvpay.mapper.UserMapper.insertUser_tb", list);
	}
}
