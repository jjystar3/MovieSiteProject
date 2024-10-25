package com.example.demo.bjh.service;

import com.example.demo.bjh.dto.MemberDTO;
import com.example.demo.bjh.entity.MemberEntity;

public interface MemberService {

	boolean signUp(MemberDTO dto); // 회원가입
	
	MemberDTO memberRead(String id); // 회원 단건 조회
	
		default MemberDTO entityToDto(MemberEntity entity) {
			MemberDTO dto = MemberDTO.builder()
					.id(entity.getId())
					.password(entity.getPassword())
					.name(entity.getName())
					.nickName(entity.getNickName())
					.eMail(entity.getEMail())
					.role(entity.getRole())
					.regDate(entity.getRegDate())
					.modDate(entity.getModDate())
					.build();

			return dto;
		}

		
		default MemberEntity dtoToEntity(MemberDTO dto) {
			MemberEntity entity = MemberEntity.builder()
					.id(dto.getId())
					.password(dto.getPassword())
					.name(dto.getName())
					.nickName(dto.getNickName())
					.eMail(dto.getEMail())
					.build();
			return entity;
		}
}
