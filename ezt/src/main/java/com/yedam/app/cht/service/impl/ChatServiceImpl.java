package com.yedam.app.cht.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.cht.mapper.ChatMapper;
import com.yedam.app.cht.service.ChatRoomVO;
import com.yedam.app.cht.service.ChatService;
import com.yedam.app.cht.service.ChatVO;
import com.yedam.app.cht.service.UsersChatRoomVO;

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

	@Transactional
	@Override
	public int createRoom(ChatRoomVO crvo) {
		int result = chatMapper.insertChatRoom(crvo);
		crvo.getUsersNos().forEach(e -> {
			UsersChatRoomVO ucrvo = new UsersChatRoomVO();
			ucrvo.setChatRoomNo(crvo.getChatRoomNo());
			ucrvo.setUsersNo(e);
			chatMapper.insertChatRoom2User(ucrvo);
		});
		ChatVO cvo = new ChatVO();
		cvo.setUsersNo(crvo.getUsersNos().get(1));
		cvo.setChatRoomNo(crvo.getChatRoomNo().toString());
		cvo.setContent("채팅방이 생성되었습니다.");
		this.addChat(cvo);
		return result;
	}

}
