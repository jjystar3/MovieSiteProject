package com.example.demo.movies.dto;

import java.util.Date;
import java.util.List;

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
    String title;
    String overview;
    String posterPath;
    String backdropPath;
    String videoPath;
    Date releaseDate;
    String directors;
    String actors;

}
