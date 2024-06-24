package com.yedam.app.cht.mapper;

import java.util.List;

import com.yedam.app.cht.service.ChatRoomVO;
import com.yedam.app.cht.service.ChatVO;

public interface ChatMapper {
	// 내 채팅방 조회
	public List<ChatRoomVO> selectMyRooms(int usersNo);
	
	// 채팅방 만들기
	public int insertRoom(int others);
	
	// 채팅방 찾기
	public ChatRoomVO selectChatRoom(int chatRoomNo);
	
	// 채팅방의 채팅 조회
	public List<ChatVO> selectChatHistory(int chatRoomNo);
	
	// 채팅 보내기
	public int insertChat(ChatVO chatVO);
	
}
