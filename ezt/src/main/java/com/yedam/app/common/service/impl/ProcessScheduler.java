package com.yedam.app.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yedam.app.alm.service.AlarmVO;
import com.yedam.app.alm.web.StompAlarmController;
import com.yedam.app.api.service.NhDevService;
import com.yedam.app.common.mapper.ProcessMapper;
import com.yedam.app.pay.mapper.PayMapper;
import com.yedam.app.pay.service.PayVO;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessScheduler {


	@Autowired NhDevService nhDevSvc;
	@Autowired ProcessMapper procMap;
	@Autowired PayMapper payMapper;
	@Autowired StompAlarmController sac;


	/*
	 * cron
	 * 첫 번째 * 부터
	 * 초(0-59)
	 * 분(0-59)
	 * 시간(0-23)
	 * 일(1-31)
	 * 월(1-12)
	 * 요일(0-6)(0: 일, 1: 월, 2:화, 3:수, 4:목, 5:금, 6:토)
	 */
	
	@Scheduled(fixedDelay = 300000)
	public void every5min() {

		log.info("+++++++++++++++++++++++++Start Schedule");
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

	// 매일 오후 14시에 실행
//	@Scheduled(cron = "0 0 14 * * *")
//	@Scheduled(fixedRate = 10000)
	public void doAt14() {
		List<Map<String, String>> list = payMapper.selectPaymentDtInfo();
		boolean nh = false;
		if (!list.isEmpty()) {
			list.forEach(e -> {
				if (e.get("WORKERBANKCODE").equals("011") || e.get("WORKERBANKCODE").equals("012")) {
					nhDevSvc.receivedTransferAccountNumber(e)
							// .doOnError(error -> log.error( "error" + error.toString())) 결과 전부 200번대로 나와서 안됨
							.doOnSuccess(success -> {

								Map map2 = (Map) success.get("Header");

								String Rpcd = (String) map2.get("Rpcd");

								if (!Rpcd.equals("00000")) {
									log.info(success.values().toString());
									// 대금 지급 여부 변경 : 'Y'
									payMapper.updatePaymentTf(
											Integer.parseInt(String.valueOf(e.get("CONTRACTDETAILNO"))));
								}

							}).subscribe();
				} else {
					nhDevSvc.receivedReceivedTransferOtherBank(e).doOnSuccess(success -> {

						Map map2 = (Map) success.get("Header");

						String Rpcd = (String) map2.get("Rpcd");

						if (!Rpcd.equals("00000")) {
							log.info(success.values().toString());
							// 대금 지급 여부 변경 : 'Y'
							payMapper.updatePaymentTf(Integer.parseInt(String.valueOf(e.get("CONTRACTDETAILNO"))));
						}

					}).subscribe();
				}
			});
		}
		log.info("its 2 o'clock");
	}
}
