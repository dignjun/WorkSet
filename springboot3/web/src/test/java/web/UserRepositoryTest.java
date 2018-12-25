package web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import web.helloworld.dao.UserRepository;
import web.helloworld.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes=Application.class)
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	@Test
	public void test(){
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.LONG);
		String formattedDate = dateFormat.format(date);
		
		userRepository.save(new User("aa1", "aa@126.com", "aa", "aa123456",formattedDate));
        userRepository.save(new User("bb2", "bb@126.com", "bb", "bb123456",formattedDate));
        userRepository.save(new User("cc3", "cc@126.com", "cc", "cc123456",formattedDate));
        
        Assert.assertEquals(9, userRepository.findAll().size());
        Assert.assertEquals("bb", userRepository.findByUserNameOrEmail("bb", "cc@126.com").getNickName());
        userRepository.delete(userRepository.findByUserName("aa1"));
	}
}
