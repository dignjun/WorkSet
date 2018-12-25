package web.helloworld.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import web.helloworld.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserName(String userName);
	User findByUserNameOrEmail(String userName, String email);
}
