package com.example.demo.member.entity;

import com.example.demo.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tbl_member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(length = 50, unique = true)
    private String username;  // Nullable username ...zzz
	
    @NotBlank
    @Size(min = 8, max = 100) // Minimum 8 characters for security reasons
    @Column(length = 100, nullable = false)
    private String password;

    @NotBlank
    @Email // Ensures a valid email format
    @Size(max = 200)
    @Column(length = 200, nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING) // Store as a string in the database
    @Column(length = 100, nullable = false)
    @Builder.Default
    private final Role role = Role.ROLE_USER;

    // Enum for role types
    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }

}
