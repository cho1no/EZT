package com.yedam.app.cht.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.cht.service.ChatRoomVO;
import com.yedam.app.cht.service.ChatService;
import com.yedam.app.cht.service.ChatVO;
import com.yedam.app.sgi.service.LoginUserVO;

@RestController
public class ChatRestApi {
	@Autowired
    ChatService csv;
	
	@GetMapping("getRooms")
	public List<ChatRoomVO> getRooms(Model model, @AuthenticationPrincipal LoginUserVO vo) {
    	return csv.getMyRooms(Integer.parseInt(vo.getUsername()));
    }
	
	@GetMapping("getChats/{chatRoomNo}")
	public List<ChatVO> getChats(@PathVariable int chatRoomNo){
		return csv.getChatHistory(chatRoomNo);
	}
}
