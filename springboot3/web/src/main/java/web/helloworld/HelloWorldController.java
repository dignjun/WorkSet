package web.helloworld;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.helloworld.domain.User;

@RestController
public class HelloWorldController {
	@RequestMapping("/getUser")
	public User getUser(){
		User user = new User("11","22","33","44","55");
		user.setUserName("helloworld");
		user.setPassWord("12345");
		return user;
	}
}
