package com.yedam.app;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.alm.service.AlarmVO;
import com.yedam.app.alm.web.StompAlarmController;
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
	@Autowired StompAlarmController sac;
	@Test
	public void testCheck() {
		List<PayVO> payList = procMap.selectPayList();
		for (PayVO payVO : payList) {
			String Vran = payVO.getVirtualAccount();
			int price = payVO.getPrice();
			List<Object> checkVran = nhDevSvc.getVirtualAcRecieveList(Vran);
			int vranSum = 0;
			for (Object recieve : checkVran) {
				Map<String, String> map = (Map<String, String>) recieve;
				vranSum += Integer.parseInt(map.get("MnrcAmt"));
			}
			if (price <= vranSum) {
				log.info(payVO.getPayNo().toString());
				int result = procMap.updateRequestState(payVO.getRequestNo());
				if (result > 0) {
					AlarmVO alarm = new AlarmVO();
					alarm.setUsersNo(payVO.getRequester());
					alarm.setTitle("결제 상태 업데이트");
					alarm.setContent("결제가 완료되었습니다.");
					sac.message(alarm);
					log.info("SUCCESS+++++++++++++++++++++++++++++++++++++++++++++++++++++");
				}
			}
		}
	}
}
