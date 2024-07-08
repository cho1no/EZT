package com.yedam.app.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yedam.app.api.service.NhDevService;
import com.yedam.app.pay.mapper.PayMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessScheduler {

	@Autowired
	NhDevService nhDevSvc;
	@Autowired
	PayMapper payMapper;

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
