package com.yedam.app.sgi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// test
	//@Bean
	
	
	@Bean
	SecurityFilterChain filterChin(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.antMatchers("/", "/all").permitAll()
				.antMatchers("/user/**").hasAnyRole("USER")
				.antMatchers("/worker/**").hasAnyRole("WORKER")
				.antMatchers("/admin/**").hasAnyAuthority("ROLE ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.defaultSuccessUrl("/all")
			.and()
			.logout()
				.logoutSuccessUrl("/all");
		
		return http.build();
	}
}
