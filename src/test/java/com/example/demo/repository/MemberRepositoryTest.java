package com.example.demo.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.MemberEntity;
import com.example.demo.service.MemberService;

@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	void 회원등급_등록() {
		
		MemberDTO dto = MemberDTO.builder()
				.id("user1")
				.password("1234")
				.name("이름1")
				.nickName("별명1")
				.eMail("Mail1")
				.build();
		
		memberService.signUp(dto);
	}
	
	@Test
	void 관리자등급_등록() {
		
		MemberDTO dto = MemberDTO.builder()
				.id("admin2_admin")
				.password("1234")
				.name("이름2")
				.nickName("별명2")
				.eMail("Mail2")
				.build();
		
		memberService.signUp(dto);
	}
	
	@Test
	void 회원_단건조회() {
		Optional<MemberEntity> optional = memberRepository.findById("user1");
		if(optional.isPresent()) {
			MemberEntity entity = optional.get();
			System.out.println(entity);
		}
	}
}
