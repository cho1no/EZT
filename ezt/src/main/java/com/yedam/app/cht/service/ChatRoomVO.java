package com.yedam.app.cht.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;

@Data
public class ChatRoomVO {
	private String chatRoomNo; // DB: Integer -> test 용으로 String 사용
	private String title;
	private String content;
	private Date updateDt;
	private Set<WebSocketSession> sessions = new HashSet<>();
    public static ChatRoomVO create(String title){
    	ChatRoomVO room = new ChatRoomVO();

        room.chatRoomNo = UUID.randomUUID().toString();
        room.title = title;
        return room;
    }
}
