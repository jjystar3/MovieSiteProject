package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.MemberEntity;
import com.example.demo.entity.RoleEnum;
import com.example.demo.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	String admin = "_admin"; // 
	
	@Autowired
	MemberRepository memberRepository;

//	@Autowired
//	PasswordEncoder passwordEncoder;

	@Override
	public boolean signUp(MemberDTO dto) {
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String id = dto.getId();
		MemberDTO getDto = memberCheck(id);
		if (getDto != null) {
			return false;
		}

		MemberEntity entity = dtoToEntity(dto);
		String enPw = passwordEncoder.encode(entity.getPassword());
		entity.setPassword(enPw);

		if (id.endsWith(admin)) {
			entity.setId(id.replace(admin, ""));
			entity.setRole(RoleEnum.ROLE_ADMIN);
		} else {
			entity.setRole(RoleEnum.ROLE_USER);
		}
		memberRepository.save(entity);
		return true;
	}

	@Override
	public MemberDTO memberCheck(String id) {
		
		Optional<MemberEntity> optional = memberRepository.findById(id);
		if (optional.isPresent()) {
			MemberEntity entity = optional.get();
			return entityToDto(entity);
		} else {
			return null;
		}

	}

}
