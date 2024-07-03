package com.yedam.app.common.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessScheduler {
//	@Scheduled(fixedRate = 1000)
    public void fixedRate() {
        log.info("fixedRate Scheduler");
    }
}
