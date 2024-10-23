package com.example.demo.movies.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	Long movieId;
	
	Boolean adult;

	@Column(length = 255)
	String title;

	@Lob
	String overview;

	@Column(length = 255)
	String posterPath;

	@Column(length = 255)
	String backdropPath;

	@Column(length = 255)
	String videoPath;
	
//	@Temporal(TemporalType.DATE)
	LocalDate releaseDate;

	@Column(columnDefinition = "TEXT")
	String directors;

	@Column(columnDefinition = "TEXT")
	String actors;
}
