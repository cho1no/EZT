package com.yedam.app.alm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.yedam.app.alm.service.AlarmService;
import com.yedam.app.alm.service.AlarmVO;
import com.yedam.app.cht.web.StompChatController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Controller
@RequiredArgsConstructor
@Log4j2
public class StompAlarmController {
	
	@Autowired
	AlarmService asc;
	
	private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
	
	@MessageMapping(value = "/alarm/message")
    public void message(AlarmVO message){
//		log.info(message);
		asc.sendAlarm(message);
        template.convertAndSend("/sub/alarm/" + message.getUsersNo(), message);
    }
}
