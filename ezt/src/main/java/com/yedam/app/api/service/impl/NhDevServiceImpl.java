package com.yedam.app.api.service.impl;

import java.util.HashMap;
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
	private static final String MOTHER_AC = "3020000011035";
	private static final String FINOWNER = "최원호";
	
	@Autowired
	WebClient webClient;
	
	@Override
	public Mono<Map> getVirtualAc() {
		String ApiNm = "OpenVirtualAccount";
		String APISvcCd = "TEST_API_G";
		Map<String, Object> map = new HashMap<>();
		NhDevHeaderDto dto = new NhDevHeaderDto(ApiNm, APISvcCd);
		map.put("Header", dto);
		map.put("Dpnm", FINOWNER);
		map.put("MnrcClamt", "");
		map.put("MnrcClsnDt", "");
		return webClient.post()
				.uri(URL + ApiNm + TLD)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(map)
				.retrieve()
				.bodyToMono(Map.class);
	}

	@Override
	public Mono<Map> getVirtualAcRecieveList(String Vran) {
		String ApiNm = "VirtualAccountReceivedListInquiry";
		String APISvcCd = "10B_001_00";
		Map<String, Object> map = new HashMap<>();
		NhDevHeaderDto dto = new NhDevHeaderDto(ApiNm, APISvcCd);
		map.put("Header", dto);
		map.put("Insymd", "20240601");
		map.put("Ineymd", "20240702");
		map.put("Vran", Vran);
		map.put("Lnsq", "");
		map.put("PageNo", "1");
		return webClient.post()
				.uri(URL + ApiNm + TLD)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(map)
				.retrieve()
				.bodyToMono(Map.class);
	}
	
	@Override
	public Mono<Map> receivedTransferAccountNumber(Map<String, String> input){
		String ApiNm = "ReceivedTransferAccountNumber";
		String APISvcCd = "ReceivedTransferA";
		Map<String, Object> map = new HashMap<>();
		NhDevHeaderDto dto = new NhDevHeaderDto(ApiNm, APISvcCd);
		map.put("Header", dto);
		map.put("Bncd", "은행코드");
		map.put("Acno", "계좌번호");
		map.put("Tram", "가격");
		map.put("DractOtlt", "출금계좌인자내용");
		map.put("MractOtlt", "입금계좌인자내용");
		return webClient.post()
				.uri(URL + ApiNm + TLD)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(map)
				.retrieve()
				.bodyToMono(Map.class);
	}
	
	@Override
	public Mono<Map> receivedReceivedTransferOtherBank(Map<String, String> input){
		String ApiNm = "ReceivedTransferOtherBank";
		String APISvcCd = "ReceivedTransferA";
		Map<String, Object> map = new HashMap<>();
		NhDevHeaderDto dto = new NhDevHeaderDto(ApiNm, APISvcCd);
		map.put("Header", dto);
		map.put("Bncd", "은행코드");
		map.put("Acno", "계좌번호");
		map.put("Tram", "가격");
		map.put("DractOtlt", "출금계좌인자내용");
		map.put("MractOtlt", "입금계좌인자내용");
		return webClient.post()
				.uri(URL + ApiNm + TLD)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(map)
				.retrieve()
				.bodyToMono(Map.class);
	}

	@Override
	public Mono<Map> paymentPayoutAccountTransfer() {
		String ApiNm = "PaymentPayoutAccountTransfer";
		String APISvcCd = "10B_002_00";
		Map<String, Object> map = new HashMap<>();
		NhDevHeaderDto dto = new NhDevHeaderDto(ApiNm, APISvcCd);
		map.put("Header", dto);
		map.put("Tram", "거래금액");
		map.put("Acno", "계좌번호를입력하세요");
		return webClient.post()
				.uri(URL + ApiNm + TLD)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(map)
				.retrieve()
				.bodyToMono(Map.class);
	}
}
