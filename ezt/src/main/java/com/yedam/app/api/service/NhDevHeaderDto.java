package com.yedam.app.api.service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NhDevHeaderDto {
	@JsonProperty("ApiNm")
	private String apiNm; // API명 필드100 필수
    @JsonProperty("Tsymd")
	private String tsymd; // 전송일자 필드 8 필수
    @JsonProperty("Trtm")
	private String trtm; //	전송시각	필드	6 필수
    @JsonProperty("Iscd")
	private String iscd; //	기관코드	필드	6 필수
    @JsonProperty("FintechApsno")
	private String fintechApsno; //	핀테크 앱 일련번호 필드3 	필수 (test 001)
    @JsonProperty("ApiSvcCd")
	private String apiSvcCd; 	 //	API서비스코드 필드 20 필수
    @JsonProperty("IsTuno")
	private String istuno; 		 // 기관거래고유번호 필드 20 필수
    @JsonProperty("AccessToken")
	private String accessToken;  //	인증키 필드 64 필수
	
	public NhDevHeaderDto(String ApiNm, String APISvcCd){
		// 현재 날짜, 시간 문자열로
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss");
		Date now = new Date();
		
		this.apiNm = ApiNm;
		this.tsymd = sdfDay.format(now);
		this.trtm = sdfTime.format(now);
		this.iscd = "002527";
		this.fintechApsno = "001";
		this.apiSvcCd = APISvcCd;
		this.istuno = generateIstuno(this.tsymd+this.trtm);
		this.accessToken = "01215f6721182d337795517ae4a80b21a652dc30d9dd6c7ebce415eee5c259e9";
		
	}
	public String generateIstuno(String front) {
    	SecureRandom random = new SecureRandom();
        String Iscd = front;
        for (int i = 0; i < 6; i++) {
        	Iscd += random.nextInt(10);
        }
        return Iscd;
    }
}
