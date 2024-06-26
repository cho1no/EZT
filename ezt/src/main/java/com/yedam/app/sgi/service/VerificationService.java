package com.yedam.app.sgi.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class VerificationService {
	public ResponseEntity<?> verifyIdentity(Map<String, String> request) {
		String url = "https://datahub-dev.scraping.co.kr/scrap/docInq/gov/ResidentPromotionCommittee";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Token 24c44dac440f4ba183caa2939084694118f00a5f");
		headers.set("Content-Type", "application/json;charset=UTF-8");

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		return ResponseEntity.ok(response.getBody());
	}
}
