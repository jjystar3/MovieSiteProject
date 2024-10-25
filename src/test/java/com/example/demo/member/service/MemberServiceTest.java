package com.example.demo.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.entity.Member.Role;

@SpringBootTest
public class MemberServiceTest {

	@Autowired
	MemberService service;

	@Test
	public void 회원등록() {
		MemberDTO dto = MemberDTO.builder()
				.id(3l)
				.password("1234")
				.email("user3@naver.com")
				.role(Role.ROLE_USER)
				.build();
		boolean isSuccess = service.register(dto);
		if(isSuccess) {
			System.out.println("회원이 등록되었습니다.");
		}else {
			System.out.println("중복된 회원 입니다.");
		}
	}

}
