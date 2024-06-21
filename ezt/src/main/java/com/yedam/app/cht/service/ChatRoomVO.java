package com.yedam.app.cht.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;

@Data
public class ChatRoomVO {
	private Integer chatRoomNo;
	private String title;
	private String content;
	private Date updateDt;
	private Set<WebSocketSession> sessions = new HashSet<>();
}
