package com.example.demo.movies.service;

import org.springframework.data.domain.Page;

import com.example.demo.movies.dto.MoviesDTO;
import com.example.demo.movies.entity.Movies;

public interface MoviesService {

	Page<MoviesDTO> getList(int pageNumber);
	
	MoviesDTO read(int id);
		
	// DTO -> Entity
	default Movies dtoToEntity(MoviesDTO dto) {

		Movies entity = Movies.builder()
				.movieId(dto.getMovieId())
				.title(dto.getTitle())
				.description(dto.getDescription())
				.posterPath(dto.getPosterPath())
				.backdropPath(dto.getBackdropPath())
				.releaseDate(dto.getReleaseDate())
				.director(dto.getDirector())
				.mainActor(dto.getMainActor())
				.build();
		
		return entity;
	}

	// Entity -> DTO
	default MoviesDTO entityToDTO(Movies entity) {

		MoviesDTO dto = MoviesDTO.builder()
				.movieId(entity.getMovieId())
				.title(entity.getTitle())
				.description(entity.getDescription())
				.posterPath(entity.getPosterPath())
				.backdropPath(entity.getBackdropPath())
				.releaseDate(entity.getReleaseDate())
				.director(entity.getDirector())
				.mainActor(entity.getMainActor())
				.build();

		return dto;
	}
}
