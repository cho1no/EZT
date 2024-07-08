package com.yedam.app.alm.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.alm.service.AlarmService;
import com.yedam.app.alm.service.AlarmVO;

@RestController
@RequestMapping("/alm")
public class AlarmRestController {
	@Autowired
	AlarmService alarmService;
	
	@GetMapping("/alarm/{usersNo}")
	public List<AlarmVO> alarmList(@PathVariable int usersNo){
		return alarmService.getAlarmList(usersNo);
	}
	
	@GetMapping("/alarmDetail/{alarmNo}")
	public AlarmVO alarmDetail(@PathVariable int alarmNo){
		return alarmService.getAlarm(alarmNo);
	}
	
	@GetMapping("/notReadAlarm/{usersNo}")
	public int notReadAlarm(@PathVariable int usersNo) {
		return alarmService.getCntNotRead(usersNo);
	}
	
	@PutMapping("/alarmRead/{alarmNo}")
	public String alarmRead(@PathVariable int alarmNo) {
		String result = null;
		if (alarmService.readAlarm(alarmNo) > 0) {
			result = "SUCC";
		} else {
			result = "FAIL";
		}
		return result;
	}
	
	@PutMapping("/allAlarmRead/{usersNo}")
	public String allAlarmRead(@PathVariable int usersNo) {
		String result = null;
		if (alarmService.readAllAlarm(usersNo) > 0) {
			result = "SUCC";
		} else {
			result = "FAIL";
		}
		return result;
	}
}
