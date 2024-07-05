package com.yedam.app.jwt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.app.jwt.service.JwtRequestDTO;
import com.yedam.app.jwt.service.JwtResponseDTO;
import com.yedam.app.jwt.service.impl.JwtTokenServiceImpl;
import com.yedam.app.sgi.service.impl.CustomerUserDetailsService;

@RestController
@RequestMapping("/adm")
@CrossOrigin("http://localhost:3000")
public class JwtController {
//	@Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenServiceImpl jwtTokenService;
//
//    @Autowired
//    private CustomerUserDetailsService userDetailsService;
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest) throws Exception {
//
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//
//        final UserDetails userDetails = userDetailsService
//                .loadUserByUsername(authenticationRequest.getUsername());
//
//        final String token = jwtTokenService.generateToken(userDetails);
//
//        return ResponseEntity.ok(new JwtResponseDTO(token));
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
}
