package com.example.demo.bjh.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class }) 
@Getter
abstract class BaseEntity {

    @CreatedDate
    LocalDateTime regDate; // 가입일

    @LastModifiedDate
    LocalDateTime modDate; // 수정일

}
