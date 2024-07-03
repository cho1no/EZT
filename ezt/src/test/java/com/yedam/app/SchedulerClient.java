package com.yedam.app;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.api.service.NhDevService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SchedulerClient {
	@Autowired NhDevService nhDevSvc;
	@Test
	public void testCheck() {
		List<Object> list = nhDevSvc.getVirtualAcRecieveList("");
		for (Object obj : list) {
			Map<String, String> map = (Map<String, String>) obj;
			log.info(map.get("MnrcAmt"));
			log.info(map.get("Vran"));
		}
	}
}
