package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.RoleEnum;
import com.example.demo.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	String admin = "_admin"; // 관리자 등급 설정 키워드
	
	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public boolean signUp(MemberDTO dto) {
		
		String getDtoId = dto.getId();
		String processId;
		
		if(getDtoId.endsWith(admin)) {
			 processId  = getDtoId.substring(0, getDtoId.length() - admin.length());
		} else {
			processId = getDtoId;
		}
		
		MemberDTO getDto = memberInquiry(processId);
		
		if (getDto != null) {
			System.out.println("이미 존재하는 아이디");
			return false;
		} 

		MemberEntity entity = dtoToEntity(dto);
		String enPw = passwordEncoder.encode(entity.getPassword());
		entity.setPassword(enPw);

		if (getDtoId.endsWith(admin)) {
			entity.setId(getDtoId.replace(admin, ""));
			entity.setRole(RoleEnum.ROLE_ADMIN);
		} else {
			entity.setRole(RoleEnum.ROLE_USER);
		}
		memberRepository.save(entity);
		return true;
	}

	@Override
	public MemberDTO memberInquiry(String id) {
		
		Optional<MemberEntity> optional = memberRepository.findById(id);
		if (optional.isPresent()) {
			MemberEntity entity = optional.get();
			return entityToDto(entity);
		} else {
			return null;
		}

	}

}
