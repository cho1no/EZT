package com.yedam.app.cht.service;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ChatRoomVO {
//	private String chatRoomNo; // DB: Integer -> test 용으로 String 사용
	private Integer chatRoomNo;
	private String title;
	private String content;
	private Date updateDt;
	private List<Integer> usersNos;
//	private Set<WebSocketSession> sessions = new HashSet<>();
    public static ChatRoomVO create(String title){
    	ChatRoomVO room = new ChatRoomVO();

//        room.chatRoomNo = UUID.randomUUID();
//        room.title = title;
        return room;
    }
}
