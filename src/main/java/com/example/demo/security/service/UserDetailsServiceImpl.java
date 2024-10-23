package com.example.demo.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.service.MemberService;
import com.example.demo.security.dto.CustomUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MemberService service;

	@Override
	public UserDetails loadUserByUsername(String email) {

		MemberDTO dto = service.findByEmail(email);

		if(dto == null) {
			throw new UsernameNotFoundException(""); 
		} else {
			return new CustomUser(dto);
		}

	}

}
