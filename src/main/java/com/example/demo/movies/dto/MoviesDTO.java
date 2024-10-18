package com.example.demo.movies.dto;

import java.util.Date;

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

    int movieId;
    String title;
    String description;
    String posterPath;
    String backdropPath;
    Date releaseDate;
    String director;
    String mainActor;

}
