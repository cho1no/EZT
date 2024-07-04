package com.yedam.app.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yedam.app.api.service.NhDevService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessScheduler {

	@Autowired NhDevService nhDevSvc;

//	@Scheduled(fixedRate = 5000)
	public void every5min() {
		log.info("fixedRate Scheduler");
		List<Object> list = nhDevSvc.getVirtualAcRecieveList("");
		for (Object obj : list) {
			Map<String, String> map = (Map<String, String>) obj;
			
			log.info(map.get("Vran"));
			log.info(obj.getClass().toString());
		}
	}
	
	// 매일 오후 14시에 실행
//	@Scheduled(cron = "0 0 14 * * *")
	public void doAt14() {
		log.info("its 2 o'clock");
	}
}
