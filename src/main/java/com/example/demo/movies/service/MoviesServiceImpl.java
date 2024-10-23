package com.example.demo.movies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

}
