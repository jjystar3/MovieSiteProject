package com.example.demo.movies.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	Long movieId;

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
	
	@Temporal(TemporalType.DATE)
	Date releaseDate;

	@Column(columnDefinition = "TEXT")
	String directors;

	@Column(columnDefinition = "TEXT")
	String actors;
}
