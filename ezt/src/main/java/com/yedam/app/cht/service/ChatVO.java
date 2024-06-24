package com.yedam.app.cht.service;

import java.util.Date;

import lombok.Data;

@Data
public class ChatVO {
	private Integer chatNo; // pk
	private String content; // content
	private Date ChatTime; // chat send time
	private String writer;  // 240624 test (not use)
	private Integer UsersNo; // sender
	private String ChatRoomNo; // target
}
