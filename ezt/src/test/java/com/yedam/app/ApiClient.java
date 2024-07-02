package com.yedam.app;

import java.security.SecureRandom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ApiClient {
    private static final int LENGTH = 20;
    private static final SecureRandom random = new SecureRandom();
	@Test
	public void generateNumericString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        log.info("test result : " + sb.toString());
    }
}
