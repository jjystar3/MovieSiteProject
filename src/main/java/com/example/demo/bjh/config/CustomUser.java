package com.example.demo.bjh.config;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.demo.bjh.dto.MemberDTO;

public class CustomUser extends User {
	
	// MemberDTO -> 인증 객체
	// User 클래스를 상속받아 해당 
	public CustomUser(MemberDTO dto) {
		
		super(dto.getId(), dto.getPassword(), Arrays.asList(new SimpleGrantedAuthority(dto.getRole().name())));
	}
}
