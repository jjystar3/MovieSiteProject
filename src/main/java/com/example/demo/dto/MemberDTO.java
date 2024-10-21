package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    String id; //아이디

    String password; //패스워드

    String name; //이름

    String nickName; // 닉네임
    
    String eMail; // 이메일
    
    RoleEnum  role; // 사용자 등급 추가 (회원: ROLE_USER, 관리자: ROLE_ADMIN)
    
    LocalDateTime regDate; // 가입일

    LocalDateTime modDate; // 수정일

    

}
