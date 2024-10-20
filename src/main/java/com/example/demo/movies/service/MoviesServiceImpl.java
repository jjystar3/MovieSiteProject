package com.example.demo.movies.service;

import java.util.Optional;

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
	public Page<MoviesDTO> getList(int pageNumber) {
		int pageNum = (pageNumber == 0) ? 0 : pageNumber - 1;
		
		Sort sort = Sort.by("movieId").descending();
		
		Pageable pageable = PageRequest.of(pageNum, 10, sort);
		Page<Movies> page = moviesRepository.findAll(pageable);
		
		Page<MoviesDTO> dtoPage = page.map(entity -> entityToDTO(entity));
		
		return dtoPage;
	}

	@Override
	public MoviesDTO read(int id) {

		Optional<Movies> optional = moviesRepository.findById(Long.valueOf(id));
		
		if(optional.isPresent()) {
			Movies movies = optional.get();
			MoviesDTO dto = entityToDTO(movies);
			return dto;
		}
		
		return null;
	}

}
