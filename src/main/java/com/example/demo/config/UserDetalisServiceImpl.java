package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;

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
		MemberDTO dto = memberService.memberInquiry(username);
		
		// 회원이 있으면 로그인 성공, 없으면 실패
		if(dto!=null) {
			return new CustomUser(dto);
		} else {
			// 에러 발생
			throw new UsernameNotFoundException("");
		}
	}

}
