package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.bjh.dto.MemberDTO;
import com.example.demo.bjh.entity.MemberEntity;
import com.example.demo.bjh.repository.MemberRepository;
import com.example.demo.bjh.service.MemberService;

@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	void 일반등급_회원가입() {
		
		MemberDTO dto = MemberDTO.builder()
				.id("user1")
				.password("1234")
				.name("회원 이름1")
				.nickName("회원 별명1")
				.eMail("회원 Mail1")
				.build();
		
		memberService.signUp(dto);
	}
	
	@Test
	void 관리자등급_회원가입() {
		
		MemberDTO dto = MemberDTO.builder()
				.id("admin1_admin")
				.password("1234")
				.name("관리자 이름1")
				.nickName("관리자 별명1")
				.eMail("관리자 Mail1")
				.build();
		
		memberService.signUp(dto);
	}
	
	@Test
	void 회원_단건조회() {
		Optional<MemberEntity> optional = memberRepository.findById("admin1");
		System.out.println("optional: " + optional);
		if(optional.isPresent()) {
			MemberEntity entity = optional.get();
			System.out.println("entity:	" + entity);
		}
	}
	
	@Test
	void 회원_전제조회() {
		List<MemberEntity> list = memberRepository.findAll();
		for(MemberEntity entity : list) {
			System.out.println(entity);
		}
	}
}
