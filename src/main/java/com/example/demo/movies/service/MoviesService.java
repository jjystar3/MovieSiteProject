package com.example.demo.movies.service;

import java.util.List;

import com.example.demo.movies.dto.MoviesDTO;
import com.example.demo.movies.entity.Movies;

public interface MoviesService {

	List<MoviesDTO> getList();
	
	MoviesDTO read(Long movieId);

	// 게시물 수정 메소드
	void modify(MoviesDTO dto);
	
	// 게시물 삭제 메소드
	void remove(Long movieId);
	
	// DTO -> Entity
	default Movies dtoToEntity(MoviesDTO dto) {

		Movies entity = Movies.builder()
				.id(dto.getId())
				.movieId(dto.getMovieId())
				.adult(dto.getAdult())
				.title(dto.getTitle())
				.overview(dto.getOverview())
				.posterPath(dto.getPosterPath())
				.backdropPath(dto.getBackdropPath())
				.videoPath(dto.getVideoPath())
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
				.adult(entity.getAdult())
				.title(entity.getTitle())
				.overview(entity.getOverview())
				.posterPath(entity.getPosterPath())
				.backdropPath(entity.getBackdropPath())
				.videoPath(entity.getVideoPath())
				.releaseDate(entity.getReleaseDate())
				.directors(entity.getDirectors())
				.actors(entity.getActors())
				.build();

		return dto;
	}
}
