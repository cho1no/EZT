package com.yedam.app;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.api.service.NhDevService;
import com.yedam.app.common.mapper.ProcessMapper;
import com.yedam.app.pay.mapper.PayMapper;
import com.yedam.app.pay.service.PayVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SchedulerClient {
	@Autowired NhDevService nhDevSvc;
	@Autowired ProcessMapper procMap;
	@Autowired PayMapper payMapper;
//	@Test
	public void testCheck() {
		List<PayVO> payList = procMap.selectPayList();
		List<Object> list = nhDevSvc.getVirtualAcRecieveList("");
		for (PayVO payVO : payList) {
//			String 
		}
		
		log.info(payList.toString());
		for (Object obj : list) {
			Map<String, String> map = (Map<String, String>) obj;
			log.info(map.toString());
			log.info(map.get("MnrcAmt") + " " + map.get("Vran"));
			
		}
	}
	@Test
	public void testJWT() {
		List<Map<String, String>> list = payMapper.selectPaymentDtInfo();
		log.info(list.toString());
		list.forEach(e -> {
//			log.info(e.toString());
			log.info(e.get("USERSNAME"));
			log.info(String.valueOf(e.get("PRICE")));
		});
	}
}
