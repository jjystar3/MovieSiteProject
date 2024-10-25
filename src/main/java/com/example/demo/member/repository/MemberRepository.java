package com.example.demo.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.member.entity.Member;

import jakarta.transaction.Transactional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long>  {

	Optional<Member> findByEmail(String email);
	
}
