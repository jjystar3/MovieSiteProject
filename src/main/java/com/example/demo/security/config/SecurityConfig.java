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
	            .requestMatchers("/movies/modify/**").hasRole("ADMIN")  // Admin-only for movie modification
	            .requestMatchers("/movies/**").hasAnyRole("ADMIN", "USER")  // Movies accessible by ADMIN and USER
	            .requestMatchers("/member/profile/**").authenticated()  // Only authenticated users can access profile
	            .requestMatchers("/member/modify/**").authenticated()  // Profile accessible by ADMIN and USER
	            .requestMatchers("/member/**").hasRole("ADMIN")  // Admin-only for other member routes
	            .anyRequest().permitAll()  // Allow all other routes
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
