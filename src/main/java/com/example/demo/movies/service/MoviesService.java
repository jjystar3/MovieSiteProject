package com.example.demo.movies.service;

import org.springframework.data.domain.Page;

import com.example.demo.movies.dto.MoviesDTO;
import com.example.demo.movies.entity.Movies;

public interface MoviesService {

	Page<MoviesDTO> getList(int pageNumber);
	
	MoviesDTO read(Long id);
		
	// DTO -> Entity
	default Movies dtoToEntity(MoviesDTO dto) {

		Movies entity = Movies.builder()
				.id(dto.getId())
				.movieId(dto.getMovieId())
				.title(dto.getTitle())
				.overview(dto.getOverview())
				.posterPath(dto.getPosterPath())
				.backdropPath(dto.getBackdropPath())
				.releaseDate(dto.getReleaseDate())
				.directors(dto.getDirectors())
				.actors(dto.getActors())
				.build();
		
		return entity;
	}

	// Entity -> DTO
	default MoviesDTO entityToDTO(Movies entity) {

		MoviesDTO dto = MoviesDTO.builder()
				.id(entity.getId())
				.movieId(entity.getMovieId())
				.title(entity.getTitle())
				.overview(entity.getOverview())
				.posterPath(entity.getPosterPath())
				.backdropPath(entity.getBackdropPath())
				.releaseDate(entity.getReleaseDate())
				.directors(entity.getDirectors())
				.actors(entity.getActors())
				.build();

		return dto;
	}
}
