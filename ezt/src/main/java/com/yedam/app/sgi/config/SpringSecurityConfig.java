package com.yedam.app.sgi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yedam.app.jwt.config.JwtAuthenticationFilter;
import com.yedam.app.jwt.service.impl.JwtProvider;
import com.yedam.app.sgi.handler.LoginFailHandler;
import com.yedam.app.sgi.service.impl.CustomerUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Autowired JwtProvider jwtTokenProvider;
	@Autowired CustomerUserDetailsService cudSvc;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    LoginFailHandler loginFailHandler() {
		return new LoginFailHandler();
	}

	
	@Bean
	SecurityFilterChain filterChin(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.antMatchers("/", "/main", "/findIdPw/**", "/review/**", "/request/**","/adm/**", "/api/**", "/verify/**", "/login/**", "/signUp/**", "/css/**", "/fonts/**", "/images/**", "/js/**").permitAll()
				.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/worker/**").hasAnyRole("WORKER", "ADMIN")
				.antMatchers("/admin/**").hasAnyAuthority("ROLE ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").usernameParameter("id")
				.defaultSuccessUrl("/main")
				.failureHandler(loginFailHandler()) //로그인실패시 처리하는 핸들러
			.and()
			.logout()
				.logoutSuccessUrl("/main")
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
			.csrf().disable();
		return http.build();
	}
	@Bean
    AuthenticationManager authenticationManager(HttpSecurity http, CustomerUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
					.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder)
					.and()
					.build();
    }
}
