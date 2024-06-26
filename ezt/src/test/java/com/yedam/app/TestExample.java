package com.yedam.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yedam.app.cht.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TestExample {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	ChatService chatService;
	@Test
	public void testEncoder() {
		String password = "1234"; //사용자가 입력
		
		// DB에 저장된 암호화된 비밀번호
		String enPwd = passwordEncoder.encode(password);
		System.out.println("enPwd : " + enPwd);
		log.info("hello");
		boolean matchResult = passwordEncoder.matches(password, enPwd);
		System.out.println("match : " + matchResult);
		
		System.out.println("hi");
	}
}
