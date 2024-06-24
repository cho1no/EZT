package com.yedam.app.cht.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.yedam.app.cht.service.ChatService;
import com.yedam.app.cht.service.ChatVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StompChatController {

	@Autowired
	ChatService csv;
	
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    
    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatVO message){
        message.setContent(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getChatRoomNo(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatVO message){
    	log.info(message);
    	csv.addChat(message);
        template.convertAndSend("/sub/chat/room/" + message.getChatRoomNo(), message);
    }
}