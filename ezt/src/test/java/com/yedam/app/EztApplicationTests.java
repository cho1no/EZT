package com.yedam.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.adm.service.AdminService;

@SpringBootTest
class EztApplicationTests {

	@Autowired
	AdminService admSvc;
	
	@Test
	void contextLoads() {
		admSvc.getUsers();
	}

}
