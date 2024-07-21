package com.yedam.app.cht.service;

import java.util.List;

public interface ChatService {
	
	public List<ChatRoomVO> getMyRooms(int usersNo);
	
	// 채팅방 단건조회
	public ChatRoomVO getChatRoom(int chatRoomNo);
	
	// 채팅 내역조회
	public List<ChatVO> getChatHistory(int chatRoomNo);
	
	// 채팅 입력
	public int addChat(ChatVO chatVO);
	
	// 채팅방 생성
	public int createRoom(ChatRoomVO crvo);
}
