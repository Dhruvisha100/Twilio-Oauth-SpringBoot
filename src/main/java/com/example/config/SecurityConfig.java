package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeHttpRequests(auth -> 
		auth.requestMatchers("/login", "/oauth/**", "register",
				"/messages", "/send", "/set-password", "/forgot-password"
				, "lst", "goback", "thisuser", "alluser").permitAll()
//				.requestMatchers("/send", "/home").authenticated().
				.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/spring-login")
						.defaultSuccessUrl("/home", true).permitAll())
				.oauth2Login(oauth -> oauth.loginPage("/login").defaultSuccessUrl("/oauth2/success"))
				.logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());

		return http.userDetailsService(userDetailsService).build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
