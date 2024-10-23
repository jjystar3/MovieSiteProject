package com.example.demo.member.dto;

import java.time.LocalDateTime;

import com.example.demo.member.entity.Member.Role;

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

    String id;

    String password;

    String name;
    
    String nickname;
    
    String email;
    
    LocalDateTime regDate;

    LocalDateTime modDate;

    Role role;

}
