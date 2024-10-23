package com.example.demo.member.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.Member.Role;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Test
	public void 회원등록() {
		
		Member member = new Member(1l,"1234", "user1@naver.com", Role.ROLE_USER);
		memberRepository.save(member);
		
		Member member2 = new Member(2l,"1234", "user2@gmail.com", Role.ROLE_ADMIN);
		memberRepository.save(member2);
	}
	
	@Test
	public void 회원목록조회() {
		List<Member> list = memberRepository.findAll();
		for(Member member : list) {
			System.out.println(member);
		}
	}

	@Test
	public void 회원단건조회() {
		Optional<Member> result = memberRepository.findById(1l);
		if(result.isPresent()) {
			Member member = result.get();
			System.out.println(member);
		}
	}

	@Test
	public void 회원수정() {
		Optional<Member> result = memberRepository.findById(2l);
		if(result.isPresent()) {
			Member member = result.get();
			// 일부 내용 변경
			member.setEmail("asdf@naver.com");
			// 데이터 업데이트
			memberRepository.save(member);
		}
	}
	
	@Test
	public void 회원삭제() {
		memberRepository.deleteById(2l);
	}

}
