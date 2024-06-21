package com.yedam.app.req.service;

import java.util.Date;

import lombok.Data;

@Data
public class RequestVO {
	private Integer requestNo;
	private int requester;
	private String cttPlace;
	private int cttPlaceArea;
	private String cttPlaceSituation;
	private int hopeCttBudget;
	private Date hopeCttStartDt;
	private Date hopeCttEndDt;
	private String postcode;
	private String address;
	private String detailAddress;
	private String title;
	private String content;
	private Date writeDt;
	private String requestState;
	private String regionCode;
}
