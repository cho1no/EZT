package com.yedam.app.adm.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yedam.app.usr.service.UserVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {

	@Autowired
	private HttpSession session;
	
    @Value("${jwt.secret}")
    private String secretKey;

    // 토큰 유효시간 30분
    private long tokenValidTime = 1 * 60 * 1000L;


    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        log.info(secretKey);
    }
    
    // JWT 토큰 생성
    public String createToken(UserVO userVO) {
        String existingToken = (String) session.getAttribute("JWT_TOKEN");
        if (existingToken != null && validateToken(existingToken)) {
            return existingToken;
        }
        Claims claims = Jwts.claims().setSubject(userVO.getUsersName()); // JWT payload 에 저장되는 정보단위
        claims.put("role", userVO.getUsersRole()); // 정보는 key / value 쌍으로 저장된다.
        claims.put("usersNo", userVO.getUsersNo().toString()); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        String newToken =  Jwts.builder()
			                .setClaims(claims) // 정보 저장
			                .setIssuedAt(now) // 토큰 발행 시간 정보
			                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
			                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
			                // signature 에 들어갈 secret값 세팅
			                .compact();
        log.info("NEW TOKEN : " +newToken);
        session.setAttribute("JWT_TOKEN", newToken);
        return newToken;
    }

    // 토큰에서 회원 정보 추출
    public UserVO getUserInfo(String token) {
    	Claims claims = Jwts.parser()
			    			.setSigningKey(secretKey)
			    			.parseClaimsJws(token)
			    			.getBody();
    	UserVO vo = new UserVO();
    	
        vo.setUsersName(claims.getSubject());
        vo.setUsersRole((String) claims.get("role"));
        vo.setUsersNo(Integer.parseInt((String) claims.get("usersNo")));
        return vo;
    }
    

    // 토큰의 유효성 + 세션과 동일한지
    public boolean validateToken(String jwtToken) {
        try {
        	String sessionToken = (String) session.getAttribute("JWT_TOKEN");
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date()) && jwtToken.equals(sessionToken);
        } catch (Exception e) {
            return false;
        }
    }
}	