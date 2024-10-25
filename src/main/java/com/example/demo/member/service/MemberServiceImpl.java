package com.example.demo.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.movies.dto.MoviesDTO;
import com.example.demo.movies.entity.Movies;

@Service
public class MemberServiceImpl implements MemberService { //서비스 인터페이스 상속받기

	@Autowired
	private MemberRepository repository; //리파지토리 필드 선언

	// 인코더 필드 선언
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Page<MemberDTO> getList(int pageNumber) {  //페이지 번호 받기
		int pageIndex = (pageNumber == 0) ? 0 : pageNumber - 1; //page index는 0부터 시작
		Pageable pageable = PageRequest.of(pageIndex, 10, Sort.by("regDate").descending());  //페이지번호, 회원데이터건수, 정렬방법을 입력하여 페이징 조건 생성
		Page<Member> entityPage = repository.findAll(pageable); //회원 목록을 페이지에 담아서 조회하기
		Page<MemberDTO> dtoPage = entityPage.map( entity -> entityToDto(entity) ); //엔티티 타입의 페이지를 DTO 타입으로 변환

		return dtoPage;
	}
	
	@Override
	public boolean register(MemberDTO dto) {
		String email = dto.getEmail();
		MemberDTO getDto = findByEmail(email);
		if (getDto != null) {
			System.out.println("사용중인 이메일입니다.");
			return false;
		}
		Member entity = dtoToEntity(dto);
		
		String enPw = passwordEncoder.encode(entity.getPassword());
		entity.setPassword(enPw);
		
		repository.save(entity);
		return true;
	}

	@Override
	public MemberDTO findById(Long id) {
		Optional<Member> result = repository.findById(id);
		if (result.isPresent()) {
			Member member = result.get();
			return entityToDto(member);
		} else {
			return null;
		}
	}

	@Override
	public MemberDTO findByEmail(String email) {
		Optional<Member> result = repository.findByEmail(email);
		if (result.isPresent()) {
			Member member = result.get();
			return entityToDto(member);
		} else {
			return null;
		}
	}

	@Override
	public void modify(MemberDTO dto) {

		Long id = dto.getId();
		Optional<Member> optional = repository.findById(id);
		
		if(optional.isPresent()) {

			Member member = optional.get();
			
			member.setUsername(dto.getUsername());
			member.setEmail(dto.getEmail());
			
			repository.save(member);
		}
		
	}

}
