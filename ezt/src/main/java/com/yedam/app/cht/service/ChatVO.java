package com.yedam.app.cht.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChatVO {
	private Integer chatNo;
	private String content;
	private Date ChatTime;
	private String writer;
	private Integer UsersNo;
	private String ChatRoomNo;
}
