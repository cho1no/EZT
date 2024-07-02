package com.yedam.app.api.service.impl;

import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.yedam.app.api.service.DatahubService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 데이터허브 Api 정보가 있습니다.
 */
@Service
@Slf4j
public class DatahubServiceImpl implements DatahubService {
	String url = "https://datahub-dev.scraping.co.kr";
	String token = "Token 24c44dac440f4ba183caa2939084694118f00a5f";

	String encSpec = "AES/CBC/PKCS5Padding";
	String encKey = "EEzzW44#VC/9JdIGV/W!ZZqZ/VpSm4Zq"; // 32 bytes key
	String encIV = "/WZJ4fVCWmSqWEmm"; // 16 bytes IV
	@Autowired
	WebClient webClient;

	@Override
	public Mono<Map> idCardCheck(Map<String, String> map) {
		String postUrl = "/scrap/docInq/gov/ResidentPromotionCommittee";

		for (String key : map.keySet()) {
			map.put(key, getEncStr(map.get(key)));
		}
		return webClient.post()         // POST method
		        .uri(url+postUrl)    // baseUrl 이후 uri
		        .header(HttpHeaders.AUTHORIZATION, token)
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(map)     // set body value
		        .retrieve()                 // client message 전송
		        .bodyToMono(Map.class);  // body type
	}
	
	@Override
	public Mono<Map> driveLicenseCheck(Map<String, String> map){
		String postUrl = "/scrap/docInq/safedriving/DriversAuthenticity";
		for (String key : map.keySet()) {
			map.put(key, getEncStr(map.get(key)));
		}
		log.info(map.toString());
		return webClient.post()         // POST method
		        .uri(url+postUrl)    // baseUrl 이후 uri
		        .header(HttpHeaders.AUTHORIZATION, token)
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(map)     // set body value
		        .retrieve()                 // client message 전송
		        .bodyToMono(Map.class);  // body type
	}

	// String Encoding
	private String getEncStr(String str) {
		String encData = null;
		try {
			// 암호화 키와 IV를 생성
			SecretKeySpec secretKey = new SecretKeySpec(encKey.getBytes("UTF-8"), "AES");
			IvParameterSpec iv = new IvParameterSpec(encIV.getBytes("UTF-8"));

			// 암호화 설정
			Cipher cipher = Cipher.getInstance(encSpec);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

			// 암호화 수행
			byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));

			// Base64 인코딩
			encData = Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encData;
	}

}
