package com.example.demo.member.service;

import org.springframework.data.domain.Page;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.entity.Member;
import com.example.demo.movies.dto.MoviesDTO;

public interface MemberService {
	
	Page<MemberDTO> getList(int pageNumber); //회원 목록조회
	
	boolean register(MemberDTO dto); //회원 등록

	MemberDTO findById(Long id); //회원 단건 조회
	
	MemberDTO findByEmail(String email);

	void modify(MemberDTO dto);
	
	//엔티티를 DTO로 변환하는 메소드
	default MemberDTO entityToDto(Member entity) {
		
		MemberDTO dto = MemberDTO.builder()
				.id(entity.getId())
				.username(entity.getUsername())
				.password(entity.getPassword())
				.email(entity.getEmail())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.role(entity.getRole())
				.build();

		return dto;
	}

	//DTO를 엔티티로 변환하는 메소드
	default Member dtoToEntity(MemberDTO dto) {

		Member entity = Member.builder()
				.id(dto.getId())
				.username(dto.getUsername())
				.password(dto.getPassword())
				.email(dto.getEmail())
				.role(dto.getRole())
				.build();
		return entity;
	}
	
}
