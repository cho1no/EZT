package com.yedam.app.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.yedam.app.api.service.NhDevHeaderDto;
import com.yedam.app.api.service.NhDevService;

import reactor.core.publisher.Mono;

@Service
public class NhDevServiceImpl implements NhDevService{
	private static final String URL = "https://developers.nonghyup.com/";
	private static final String TLD = ".nh";
	private static final String MOTHER_AC = "3020000011209";
	private static final String FINOWNER = "최원호";
	
	@Autowired
	WebClient webClient;
	
	private String getDay(int beforeDay) {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE,  -1 * beforeDay);
		return sdf.format(cal.getTime());
	}
	
	@Override
	public Mono<String> getVirtualAc() {
		String ApiNm = "OpenVirtualAccount";
		String APISvcCd = "TEST_API_G";
		Map<String, Object> map = createHeader(ApiNm, APISvcCd);
		map.put("Dpnm", FINOWNER);
		map.put("MnrcClamt", "");
		map.put("MnrcClsnDt", "");
		return sendApi(ApiNm, map).map(resp -> (String) resp.get("Vran"));
	}

	@Override
	public List<Object> getVirtualAcRecieveList(String Vran) {
		String ApiNm = "VirtualAccountReceivedListInquiry";
		String APISvcCd = "10B_001_00";
		Map<String, Object> map = createHeader(ApiNm, APISvcCd);
		map.put("Insymd", getDay(1));
		map.put("Ineymd", getDay(0));
		map.put("Vran", Vran);
		map.put("Lnsq", "");
		map.put("PageNo", "1");
		Mono<Map> mono = sendApi(ApiNm, map);
		Map<String, Object> respMap = mono.block();
		// "REC" 키의 값을 추출하여 List<Object>로 변환
        if (respMap != null && respMap.get("REC") instanceof List) {
            return (List<Object>) respMap.get("REC");
        } else {
            throw new RuntimeException("REC key is missing or not a list in the response.");
        }
	}
	
	@Override
	public Mono<Map> receivedTransferAccountNumber(Map<String, String> input){
		String ApiNm = "ReceivedTransferAccountNumber";
		String APISvcCd = "ReceivedTransferA";
		Map<String, Object> map = createHeader(ApiNm, APISvcCd);
		map.put("Bncd", input.get("WORKERBANKCODE"));
		map.put("Acno", input.get("WORKERACCOUNT"));
		map.put("Tram", String.valueOf(input.get("PRICE")));
		map.put("DractOtlt", input.get("USERSNAME"));
		map.put("MractOtlt", "이지테리어");
		return sendApi(ApiNm, map);
	}
	
	@Override
	public Mono<Map> receivedReceivedTransferOtherBank(Map<String, String> input){
		String ApiNm = "ReceivedTransferOtherBank";
		String APISvcCd = "ReceivedTransferA";
		Map<String, Object> map = createHeader(ApiNm, APISvcCd);
		map.put("Bncd", input.get("WORKERBANKCODE"));
		map.put("Acno", input.get("WORKERACCOUNT"));
		map.put("Tram", String.valueOf(input.get("PRICE")));
		map.put("DractOtlt", input.get("USERSNAME"));
		map.put("MractOtlt", "이지테리어");
		return sendApi(ApiNm, map);
	}

	@Override
	public Mono<Map> paymentPayoutAccountTransfer() {
		String ApiNm = "PaymentPayoutAccountTransfer";
		String APISvcCd = "10B_002_00";
		Map<String, Object> map = createHeader(ApiNm, APISvcCd);
		map.put("Tram", "거래금액");
		map.put("Acno", "계좌번호를입력하세요");
		return sendApi(ApiNm, map);
	}
	
	/**
	 * 헤더 생성
	 * @param ApiNm : Api이름
	 * @param APISvcCd : Api 서비스 코드
	 * @return
	 */
	private Map<String, Object> createHeader(String ApiNm, String APISvcCd){
		Map<String, Object> map = new HashMap<>();
		NhDevHeaderDto dto = new NhDevHeaderDto(ApiNm, APISvcCd);
		map.put("Header", dto);
		return map;
	}
 	
	/**
	 * Api 요청
	 * @param ApiNm : Api이름
	 * @param reqData : Request 데이터
	 * @return Map타입 Response
	 */
	private Mono<Map> sendApi(String ApiNm, Map<String, Object> reqData){
		return webClient.post()
						.uri(URL + ApiNm + TLD)
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(reqData)
						.retrieve()
						.bodyToMono(Map.class);
	}
}
