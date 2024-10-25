package com.example.demo.bjh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity extends BaseEntity { 

	@Id
	@Column(length = 50)
	String id; // 아이디

	@Column(length = 200, nullable = false)
	String password; // 패스워드

	@Column(length = 100, nullable = false)
	String name; // 이름
	
	@Column(length = 100, nullable = false)
	String nickName; // 닉네임
	
	@Column(length = 200, nullable = false)
	String eMail; // 이메일

	@Enumerated(EnumType.STRING)
	@Column(length = 100, nullable = false)
	RoleEnum role; // 사용자 등급 

}
