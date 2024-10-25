package com.example.demo.bjh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.bjh.dto.MemberDTO;
import com.example.demo.bjh.service.MemberService;

// 커스텀 인증 클래스
// 로그인 처리 서비스

@Service
public class UserDetalisServiceImpl implements UserDetailsService{

	@Autowired
	MemberService memberService;
	
	// 시큐리티 라이브러리쪽으로 반환됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 아이디로 회원 조회
		MemberDTO dto = memberService.memberRead(username);
		
		// 회원이 있으면 로그인 성공, 없으면 실패
		if(dto!=null) {
			return new CustomUser(dto); // 스프링 시큐리티에 반환
		} else {
			throw new UsernameNotFoundException(""); // 에러 발생
		}
	}

}