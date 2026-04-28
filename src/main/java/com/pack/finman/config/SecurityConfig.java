package com.pack.finman.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pack.finman.repository.UserRepository;
import com.pack.finman.security.JwtAuthFilter;
import com.pack.finman.security.JwtService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	//private final JwtAuthFilter jwtAuthFilter;
	private final UserRepository userRepository;
	 private final JwtService jwtService;
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(c -> c.disable())
				.authorizeHttpRequests(a -> a
						.requestMatchers("/api/auth/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
								"/v3/api-docs/**", "/v1/api-docs/**", "/v1/swagger-ui/**", "/v1/swagger-ui/index.html")
						.permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				//.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
// Allow H2 console frames in dev
				.headers(h -> h.frameOptions(f -> f.sameOrigin())).httpBasic(h -> {
				});
		return http.build();
	}
	
	/*
	 * @Bean public JwtAuthFilter jwtAuthFilter() { return new
	 * JwtAuthFilter(jwtService(), userDetailsService()); }
	 */
	
	@Bean
	public JwtAuthFilter jwtAuthFilter() {
	    return new JwtAuthFilter(jwtService, userDetailsService());
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
