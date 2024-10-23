package com.example.demo.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
            auth -> auth
            	.requestMatchers("/register").permitAll()
                .requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()
                .requestMatchers("/").authenticated()
                .requestMatchers("/movies/*").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/member/*").hasRole("ADMIN")
        )
        .csrf(csrf -> csrf.disable())
        .formLogin(
    		formLogin -> formLogin
	            .loginPage("/customlogin").permitAll()
	            .loginProcessingUrl("/login")
	            .permitAll()
//	            .successHandler((request, response, authentication) -> {
//	                response.sendRedirect("/");
//	            })
        )
        .logout(withDefaults());
              
        return http.build();
    }
	
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
