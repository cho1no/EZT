package com.yedam.app.api.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.yedam.app.api.service.PublicDataService;

import reactor.core.publisher.Mono;

@Service
public class PublicDataServiceImpl implements PublicDataService{
	String url = "https://apis.data.go.kr/"; // 공공데이터 api 링크 시작
	String key = "6eMcqcZyT26TeAk738FTTx9SRAIiDmg1UxWzPPNIeY+anC2ZGHvWSTORyh3zM6CRJsosVSkbEwejBJmkpABkJA==";
	
	@Autowired
	WebClient webClient; 
	
	@Override
	public Mono<Map> TechLicenseCheck(String licenseId) {
		String queryString = url;
		queryString += "B552729/kcaApiService_cq2/getCqCertificateCheck?"; // 라이센스용 추가 문자열
		queryString += "serviceKey=" + key;
		queryString += "&no=" + licenseId;
		return webClient.post()         // POST method
		        .uri(queryString)    // baseUrl 이후 uri
		        .retrieve()                 // client message 전송
		        .bodyToMono(Map.class);  // body type
	}
	
}
