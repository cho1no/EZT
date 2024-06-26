package com.yedam.app.sgi.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.sgi.service.VerificationService;

@RestController
@RequestMapping("/verify")
public class VerificationController {
	@Autowired
	private VerificationService verificationService;

	@PostMapping("/identity")
	public ResponseEntity<?> verifyIdentity(@RequestBody Map<String, String> request) {
		return verificationService.verifyIdentity(request);
	}
}
