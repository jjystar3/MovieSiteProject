package com.example.demo.security.config;

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
	            .requestMatchers("/register").permitAll()  // Permit registration to anyone
	            .requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()  // Permit static resources
	            .requestMatchers("/movies/**").hasAnyRole("ADMIN", "USER")  // Protect movies for certain roles
	            .requestMatchers("/member/**").hasRole("ADMIN")  // Protect admin routes
	            .anyRequest().permitAll()  // Allow all other routes (like "/") to be accessed by anyone
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
        .logout(logout -> logout
        	    .logoutUrl("/logout")
        	    .logoutSuccessUrl("/")//custom-logout-page
        	    .permitAll()
        	);
//        .logout(withDefaults());
              
        return http.build();
    }
	
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
