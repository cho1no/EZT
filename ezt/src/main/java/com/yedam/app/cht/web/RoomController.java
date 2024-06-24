package com.yedam.app.cht.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yedam.app.cht.service.ChatRoomRepository;
import com.yedam.app.cht.service.ChatRoomVO;
import com.yedam.app.cht.service.ChatService;
import com.yedam.app.sgi.service.LoginUsrVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final ChatRoomRepository repository;

    @Autowired
    ChatService csv;
    
    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public String rooms(Model model, @AuthenticationPrincipal LoginUsrVO vo){

        log.info("# All Chat Rooms");
        model.addAttribute("list", csv.getMyRooms(Integer.parseInt(vo.getUsername())));

        return "cht/rooms";
    }
    
    
//    @GetMapping(value = "/rooms")
//    public ModelAndView rooms(){
//    	
//    	log.info("# All Chat Rooms");
//    	ModelAndView mv = new ModelAndView("chat/rooms");
//    	
//    	mv.addObject("list", repository.findAllRooms());
//    	log.info(repository.findAllRooms());
//    	log.info(csv.getChatRoomList());
////        mv.addObject("list", csv.getChatRoomList());
//    	
//    	return mv;
//    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam String name, RedirectAttributes rttr){

        log.info("# Create Chat Room , name: " + name);
        rttr.addFlashAttribute("roomName", repository.createChatRoomVO(name));
        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("/room")
    public String getRoom(String chatRoomNo, Model model){

        log.info("# get Chat Room, roomID : " + chatRoomNo);
        
//        model.addAttribute("room", repository.findRoomById(chatRooNo));
        model.addAttribute("room", csv.getChatRoom(Integer.parseInt(chatRoomNo)));
        model.addAttribute("chatList", csv.getChatHistory(Integer.parseInt(chatRoomNo)));
        return "cht/room";
    }
    
    @GetMapping("float")
    public String goFloat() {
    	return "floating/floatingTest";
    }
}
