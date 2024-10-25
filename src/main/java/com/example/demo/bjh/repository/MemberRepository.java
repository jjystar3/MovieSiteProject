package com.example.demo.bjh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bjh.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
}
