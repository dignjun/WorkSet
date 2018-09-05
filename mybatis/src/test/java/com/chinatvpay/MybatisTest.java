package com.chinatvpay;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
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
	private static SqlSession session;

	@Before
	public void Init() {
		try {
			InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			session = sqlSessionFactory.openSession();
		} catch (IOException e) {
			System.err.println("读取配置文件异常"+e.getMessage());
		}
	}
	
	@After
	public void destory() {
		session.commit();
		session.close();
	}
	
	// 查询
	@Test
	public void selectTest(){
		Object user = session.selectOne("com.chinatvpay.mapper.UserMapper.findUserById", "1");
		System.out.println(user);
	}
	
	// foreach 批量插入，这地方神坑！！！！！！
	@Test
	public void insertTest(){
		List<User_tb> list = new ArrayList<User_tb>();
		User_tb user1 = new User_tb(5,"张三3","123456");
		User_tb user2 = new User_tb(22,"李四2","654321");
		User_tb user3 = new User_tb(31,"王武1","=-=-=-");
		list.add(user3);
		list.add(user2);
		list.add(user1);
		session.insert("com.chinatvpay.mapper.UserMapper.insertUser_tb", list);
	}
	
	// foreach 批量插入方式2，这地方神坑！！！！！！
	@Test
	public void insertTest2(){
		List<User_tb> list = new ArrayList<User_tb>();
		User_tb user1 = new User_tb(451,"柱子","12345556");
		User_tb user2 = new User_tb(2522,"桌子","65554321");
		User_tb user3 = new User_tb(020,"玻璃","=-=5-5=-");
		list.add(user3);
		list.add(user2);
		list.add(user1);
		session.insert("com.chinatvpay.mapper.UserMapper.insertUser_tb2", list);
	}
	
	// 插入单条记录
	@Test
	public void insertOneTest(){
		User_tb user_tb = new User_tb(888, "8单条8", "8记录8");
		session.insert("com.chinatvpay.mapper.UserMapper.insertOneUser_tb", user_tb);
	}
	
	// 条件查询
	@Test
	public void findUser_tbByName() {
		Object result = session.selectOne("com.chinatvpay.mapper.User_tbMapper.findUser_tbByName","单条");
		System.out.println(result);
	}
	
	// 动态sql <if> 标签元素
	@Test
	public void findUser_tbByNameLike() {
		List<Object> list = session.selectList("com.chinatvpay.mapper.User_tbMapper.findUser_tbByNameLike", null);
		System.out.println(list);
	}
	
	
}
