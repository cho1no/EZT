package com.yedam.app.sgi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

    @Bean
    LoginFailHandler loginFailHandler() {
		return new LoginFailHandler();
	}
	
	
	@Bean
	SecurityFilterChain filterChin(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.antMatchers("/", "/main", "/api/**", "/verify/**", "/login/**", "/signUp", "/signUp/**", "/css/**", "/fonts/**", "/images/**", "/js/**").permitAll()
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
				.csrf().disable();
		
		return http.build();
	}
	
	
}
