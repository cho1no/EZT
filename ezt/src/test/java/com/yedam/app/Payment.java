package com.yedam.app;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.api.service.NhDevService;
import com.yedam.app.pay.mapper.PayMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@SpringBootTest
@Slf4j
public class Payment {
	@Autowired NhDevService nhDevSvc;
	@Autowired PayMapper payMapper;
	
	@Test
	public void testCheck() {
		
		List<Map<String, String>> list = payMapper.selectPaymentDtInfo();


		boolean nh = false;
		list.forEach(e -> {
			//log.info(String.valueOf(e.get("PRICE")));
			//log.info(e.toString());
			if(e.get("WORKERBANKCODE").equals("011") || e.get("WORKERBANKCODE").equals("012")) {
				Mono<Map> mono= nhDevSvc.receivedTransferAccountNumber(e);

			}else {
				//nhDevSvc.receivedReceivedTransferOtherBank(e);
			}
		});
		
		
		
		for(Map<String, String> map : list ) {
			//log.info(String.valueOf(map.get("PRICE")));
			
//			for(Map.Entry<String, String> entry:map.entrySet()){
//				input.put(entry.getKey(), entry.getValue());
//		        String key = entry.getKey();
//		        Object value = entry.getValue();
//		        //log.info("key: " + key + " | value: " + value);
//		        if(value.equals("011") || value.equals("012")) {
//		        	nh = true;
//		        }
//			}
//			if(nh == true) {
//			log.info("true" + input.toString());
//			log.info("true" + input.get("PRICE"));
//			}else {
//				log.info("false" + input.toString());
//				log.info("false" + input.get("PRICE"));
//			}
//
//			nh = false;
		}
			

		
	}
	
}
