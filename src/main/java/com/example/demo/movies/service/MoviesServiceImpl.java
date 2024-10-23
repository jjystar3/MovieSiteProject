package com.example.demo.movies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.movies.dto.MoviesDTO;
import com.example.demo.movies.entity.Movies;
import com.example.demo.movies.repository.MoviesRepository;

@Service
public class MoviesServiceImpl implements MoviesService {

	@Autowired
	MoviesRepository moviesRepository;

	@Override
	public List<MoviesDTO> getList() {
		
		Sort sort = Sort.by("id").ascending(); //descending()
		
		List<Movies> result = moviesRepository.findAll(sort);
		
		List<MoviesDTO> list = new ArrayList<>();
		
		list = result.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
		
		return list;
	}

	@Override
	public MoviesDTO read(Long movieId) {

		Optional<Movies> optional = moviesRepository.findByMovieId(movieId);
		
		if(optional.isPresent()) {
			Movies movies = optional.get();
			MoviesDTO dto = entityToDTO(movies);
			return dto;
		}
		
		return null;
	}


	@Override
	public void modify(MoviesDTO dto) {

		// 전달받은 DOT에서 게시물 번호를 꺼내고, DB에 존제하는지 확인
		Long movieId = dto.getMovieId();
		Optional<Movies> optional = moviesRepository.findByMovieId(movieId);
		
		if(optional.isPresent()) {

			Movies movies = optional.get();
			
			// 기존 엔티티에서 제목, 내용 변경
			movies.setTitle(dto.getTitle());
			movies.setAdult(dto.getAdult());
			movies.setOverview(dto.getOverview());
			movies.setDirectors(dto.getDirectors());
			movies.setActors(dto.getActors());
			movies.setReleaseDate(dto.getReleaseDate());
			
			// 데이터베이스에 업데이트
			moviesRepository.save(movies);
		}
		
	}

	@Override
	public void remove(Long movieId) {

		// 게시물이 존재하는지 확인하고 삭제
		Optional<Movies> optional = moviesRepository.findByMovieId(movieId);

		if(optional.isPresent()) {
			
			// 게시물에 달린 댓글 먼저 삭제
//			Board board = Board.builder().no(no).build();
//			
//			commentRepository.deleteByBoard(board);
			
			// 게시물 삭제
			moviesRepository.deleteByMovieId(movieId);
		}
		
	}

}
