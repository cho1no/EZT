package com.yedam.app.cht.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.cht.mapper.ChatMapper;
import com.yedam.app.cht.service.ChatRoomVO;
import com.yedam.app.cht.service.ChatService;
import com.yedam.app.cht.service.ChatVO;

@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	ChatMapper chatMapper;

	@Override
	public List<ChatRoomVO> getMyRooms(int usersNo) {
		return chatMapper.selectMyRooms(usersNo);
	}
	
	@Override
	public ChatRoomVO getChatRoom(int chatRoomNo) {
		return chatMapper.selectChatRoom(chatRoomNo);
	}
	
	@Override
	public List<ChatVO> getChatHistory(int chatRoomNo) {
		return chatMapper.selectChatHistory(chatRoomNo);
	}

	@Override
	public int addChat(ChatVO chatVO) {
		return chatMapper.insertChat(chatVO);
	}

}
