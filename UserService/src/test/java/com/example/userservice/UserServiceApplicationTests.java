package com.example.userservice;

import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class UserServiceApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void createUser(){
//		User user = new User();
//		user.setEmail("snigdha@gmail.com");
//		user.setPassword(bCryptPasswordEncoder.encode("snigdha"));
//		userRepository.save(user);

	}

}
