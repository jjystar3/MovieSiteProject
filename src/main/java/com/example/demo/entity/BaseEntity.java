package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

// 시간을 관리하는 클래스

@MappedSuperclass // JPA가 해당클래스는 테이블로 생성하지 않음(엔티티를 스캔할 때 생략)
@EntityListeners(value = { AuditingEntityListener.class }) //엔티티가 등록되거나 수정되는지 변화를 감지하는 리스너 지정
@Getter
public class BaseEntity {
	
	@CreatedDate // insert시 현재시간을 저장
	LocalDateTime regDate; //등록일
	
	@LastModifiedDate // update시 현재시간을 저장
	LocalDateTime modDate; //수정일

}
