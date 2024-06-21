package com.yedam.app.cht.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChatVO {
	private Integer chatNo;
	private String content;
	private Date ChatTime;
	private Integer UsersNo;
	private Integer ChatRoomNo;
}
