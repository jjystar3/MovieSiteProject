package com.example.demo.movies.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tbl_movies")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movies {

	@Id
	int movieId; // 영화 아이디

	@Column(length = 255, nullable = false)
	String title; // 패스워드

	@Column(length = 255)
	String description;

	@Column(length = 255)
	String posterPath;

	@Column(length = 255)
	String backdropPath;

	Date releaseDate;

	@Column(length = 255)
	String director;

	@Column(length = 255)
	String mainActor;
}
