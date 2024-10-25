package com.example.demo.movies.dto;

import java.time.LocalDate;

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
public class MoviesDTO {

    Long movieId;
	Double popularity;
    Boolean adult;
    String title;
    String overview;
    String posterPath;
    String backdropPath;
    String videoPath;
    LocalDate releaseDate;
    String directors;
    String actors;

}
